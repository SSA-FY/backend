package ssafy.lambda.vote.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.service.MemberService;
import ssafy.lambda.membership.service.MembershipService;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.team.service.TeamService;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.dto.ResponseProfileWithPercentDto;
import ssafy.lambda.vote.dto.ResponseVoteDto;
import ssafy.lambda.vote.dto.ResponseVoteStatusDto;
import ssafy.lambda.vote.entity.Vote;
import ssafy.lambda.vote.entity.VoteInfo;
import ssafy.lambda.vote.repository.VoteInfoRepository;
import ssafy.lambda.vote.repository.VoteRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final VoteInfoRepository voteInfoRepository;
    /////////////////////////////////////////////////////
    private final MembershipService membershipService;
    private final TeamService teamService;
    private final MemberService memberService;

    @Override
    public void createVote(Long memberId, Long teamId, RequestVoteDto requestVoteDto) {
        Vote vote = Vote.builder()
                        .content(requestVoteDto.getContent())
                        .imgUrl(requestVoteDto.getBackgroundUrl())
                        .membership(
                            membershipService.findMembershipByMemberIdAndTeamId(memberId, teamId))
                        .build();
        voteRepository.save(vote);
    }

    @Override
    public void doVote(Long voteId, Long teamId, Long voterId, Long voteeId)
        throws IllegalArgumentException {

        Vote foundVote = validateVote(voteId);
        Member voter = memberService.findMemberById(voterId);
        Member votee = memberService.findMemberById(voteeId);

        // 이미 투표했는가
        if (voteInfoRepository.existsByVoteAndVoter(foundVote, voter)) {
            throw new IllegalArgumentException("The member already voted");
        }

        // 투표하기
        VoteInfo voteInfo = VoteInfo.builder()
                                    .voter(voter)
                                    .votee(votee)
                                    .vote(foundVote)
                                    .build();

        voteInfoRepository.save(voteInfo);

    }

    @Override
    public void review(Long memberId, Long voteId, String review) {

        Vote foundVote = validateVote(voteId);
        Member member = memberService.findMemberById(memberId);

        // 투표했는가
        VoteInfo foundVoteInfo = voteInfoRepository.findByVoteAndVoter(foundVote, member)
                                                   .orElseThrow(
                                                       () -> new IllegalArgumentException(
                                                           "The member hasn't voted yet")
                                                   );

        // 이미 한줄평을 남겼는가
        if (foundVoteInfo.getOpinion() != null) {
            throw new IllegalArgumentException("The member already left a review");
        }

        foundVoteInfo.setOpinion(review);
        voteInfoRepository.save(foundVoteInfo);
    }


    public List<ResponseProfileWithPercentDto> voteResult(Long voteId) {
        Vote foundVote = validateVote(voteId);

        List<ResponseProfileWithPercentDto> resultOfDto = voteRepository
            .findVoteInfoByCnt(voteId)
            .stream()
            .map(
                row -> {
                    Long memberId = ((Number) row[0]).longValue();
                    //                Long cnt = ((Number) ob[1]).longValue();
                    Double percent = ((Number) row[2]).doubleValue();

                    // TODO 쿼리 최대 6번 날라감, 최적화 필요
                    // MemberRepository에서 List<Member> findByIdIn(List<Long> memberIds); 메서드 선언 후
                    // List<Long> topVoteeIds를 파라미터로 넘겨줘서 컬렉션 조회 1번으로 줄이기
                    Member choosedMember = memberService.findMemberById(memberId);

                    return new ResponseProfileWithPercentDto(
                        choosedMember.getName(),
                        choosedMember.getProfileImgUrl(),
                        percent);
                }
            )
            .toList();

        return resultOfDto;

    }

    /* validateVote
     * 투표가 유효한지 검사하는 함수
     * 투표가 존재하는지, 진행중인지 확인한다
     * */
    private Vote validateVote(Long voteId) {
        Vote foundVote = voteRepository.findById(voteId)
                                       .orElseThrow(
                                           () -> new IllegalArgumentException("vote doesn't exist")
                                       );
        if (foundVote.getExpiredAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("vote is over");
        }
        return foundVote;
    }


    @Override
    public List<ResponseVoteDto> getVoteListByMember(Long memberId, Long teamId) {
        Member member = memberService.findMemberById(memberId);
        Team team = teamService.findTeamById(teamId);
        return voteRepository.findVoteByVoterAndTeam(member, team);
    }

    /**
     * sortByVoteStatus : 투표 상태에 따른 정렬 로직 팀 리스트를 받고 각각의 팀에 대해 아직 투표했는지 안했는지를 판단 이를 통해
     * ResponseSortVoteDto에 2개의 리스트에 각각 저장 but 팀 리스트의 size()만큼 쿼리가 나간다.
     * TODO: 주석 처리 된 부분에 파라미터를 통해 실제 엔티티 받아와서 로직 처리 (entityManager 삭제 후 주석 해제)
     *
     * @param memberId
     * @param teamIds
     * @return ResponseSortVoteDto(멤버가 모든 투표에 참여한 팀리스트, 투표가 아직 남은 팀 리스트)
     */
    @Override
    public ResponseVoteStatusDto sortByVoteStatus(Long memberId, List<Long> teamIds) {
        ResponseVoteStatusDto responseVoteStatusDto = new ResponseVoteStatusDto();
        List<Object[]> inCompleteVotes = new ArrayList<>();

        Member member = memberService.findMemberById(memberId);
        for (Long teamId : teamIds) {
            Vote findResult = voteRepository.findInCompleteVoteByVoterAndTeam(member,
                teamService.findTeamById(teamId));
            if (findResult == null) {
//                responseSortVoteDto.getCompletedTeams()
//                                   .add(team.getTeamId());
            } else {
//                inCompleteVotes.add(new Object[]{team.getTeamId(), findResult});
            }
        }

        Collections.sort(inCompleteVotes, (o1, o2) -> ((Vote) o1[1]).getExpiredAt()
                                                                    .compareTo(
                                                                        ((Vote) o2[1]).getExpiredAt()));
        inCompleteVotes.stream()
                       .forEach((o1) -> responseVoteStatusDto.getInCompletedTeams()
                                                             .add((Long) o1[0]));
        return responseVoteStatusDto;
    }

}
