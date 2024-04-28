package ssafy.lambda.vote.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.dto.ResponseProfileWithPercentDto;
import ssafy.lambda.vote.dto.ResponseVoteStatusDto;
import ssafy.lambda.vote.dto.ResponseVoteDto;
import ssafy.lambda.vote.entity.Vote;
import ssafy.lambda.vote.entity.VoteInfo;
import ssafy.lambda.vote.repository.VoteInfoRepository;
import ssafy.lambda.vote.repository.VoteRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService{

    private final VoteRepository voteRepository;
    private final VoteInfoRepository voteInfoRepository;

    // TODO : MemberShip

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createVote(Long memberId, Long teamId, RequestVoteDto requestVoteDto) {
        // TODO : memberId와 teamId로 membership 객체 받아와 Vote 생성시 넣기
        Vote vote = Vote.builder()
                .content(requestVoteDto.getContent())
                .imgUrl(requestVoteDto.getBackgroundUrl())
                .membership(null)
            .build();
        voteRepository.save(vote);
    }

    @Override
    public void doVote(Long voteId, Long teamId, Long memberId, Long choosedMemberId) throws IllegalArgumentException{

        Vote foundVote = validateVote(voteId);
        Member member = entityManager.find(Member.class, memberId);
        Member choosedMember = entityManager.find(Member.class, choosedMemberId);

        if(member==null || choosedMember == null){
            throw new IllegalArgumentException("member doesn't exist");
        }

        // 이미 투표했는가
        if(voteInfoRepository.existsByVoteAndMember(foundVote, member)){
            throw new IllegalArgumentException("The member already voted");
        }

        // 투표하기
        VoteInfo voteInfo
                = VoteInfo.builder()
                    .member(member)
                    .choosedMember(choosedMember)
                    .vote(foundVote)
                .build();

        voteInfoRepository.save(voteInfo);

    }

    @Override
    public void review(Long memberId, Long voteId, String review){

        Vote foundVote = validateVote(voteId);
        Member member = entityManager.find(Member.class, memberId);

        if(member==null){
            throw new IllegalArgumentException("member doesn't exist");
        }

        // 투표했는가
        VoteInfo foundVoteInfo = voteInfoRepository.findByVoteAndMember(foundVote, member).orElseThrow(
            ()-> new IllegalArgumentException("user hasn't voted yet")
        );

        // 이미 한줄평을 남겼는가
        if(foundVoteInfo.getOpinion() != null){
            throw new IllegalArgumentException("user already left a review");
        }

        foundVoteInfo.setOpinion(review);
        voteInfoRepository.save(foundVoteInfo);
    }


    public List<ResponseProfileWithPercentDto> voteResult(Long voteId){

        Vote foundVote = validateVote(voteId);

        String sql =  "SELECT choosed_member_id, COUNT(*) as cnt, ROUND(COUNT(*) / SUM(COUNT(*)) OVER (), 2) " +
            "FROM vote_info " +
            "WHERE vote_id = :voteId " +
            "GROUP BY choosed_member_id " +
            "ORDER BY cnt DESC " +
            "LIMIT 6";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("voteId", voteId);

        List<Object[]> resultOfQuery = query.getResultList();
        List<ResponseProfileWithPercentDto> resultOfDto = resultOfQuery.stream().map(
            row ->{
                Long memberId = ((Number) row[0]).longValue();
//                Long cnt = ((Number) ob[1]).longValue();
                Double percent = ((Number) row[2]).doubleValue();

                // 쿼리 최대 6번 날라감, 최적화 필요
                Member choosedMember = entityManager.find(Member.class, memberId);

                // 투표받은사람이 존재하지 않음(그 사이 회원탈퇴함)
//                if(choosedMember == null) {
//                    throw new IllegalArgumentException("user doesn't exist");
//                }

                return new ResponseProfileWithPercentDto(
                    choosedMember.getName(),
                    choosedMember.getProfileImgUrl(),
                    percent);
            }

        ).toList();

        return resultOfDto;
    }


    /* validateVote
    * 투표가 유효한지 검사하는 함수
    * 투표가 존재하는지, 진행중인지 확인한다
    * */
    private Vote validateVote(Long voteId){
        Vote foundVote = voteRepository.findById(voteId).orElseThrow(
            ()-> new IllegalArgumentException("vote doesn't exist")
        );
        if(foundVote.isProceeding() == false){
            throw new IllegalArgumentException("vote is over");
        }
        return foundVote;
    }


    @Override
    public List<ResponseVoteDto> getUserVote(Long memberId, Long teamId) {
        //TODO: entityManager 사용 코드 지우고 Member, Team Service에서 받아오기
        //TODO: 예외처리 로직 삭제
        Member member = entityManager.find(Member.class, memberId);
        Team team = entityManager.find(Team.class, teamId);

        if (member == null) {
            throw new IllegalArgumentException("member doesn't exist");
        }
        if (team == null) {
            throw new IllegalArgumentException("taem doesn't exist");
        }

        return voteRepository.findVoteByMemberAndTeam(member, team);
    }

    /**
     * sortByVoteStatus : 투표 상태에 따른 정렬 로직
     * 팀 리스트를 받고 각각의 팀에 대해 아직 투표했는지 안했는지를 판단
     * 이를 통해 ResponseSortVoteDto에 2개의 리스트에 각각 저장
     * but 팀 리스트의 size()만큼 쿼리가 나간다.
     * TODO: 주석 처리 된 부분에 파라미터를 통해 실제 엔티티 받아와서 로직 처리 (entityManager 삭제 후 주석 해제)
     * @param memberId
     * @param teamIds
     * @return ResponseSortVoteDto(유저가 모든 투표에 참여한 팀리스트, 투표가 아직 남은 팀 리스트)
     */
    @Override
    public ResponseVoteStatusDto sortByVoteStatus(Long memberId, List<Long> teamIds) {
        ResponseVoteStatusDto responseVoteStatusDto = new ResponseVoteStatusDto();
        List<Object[]> inCompleteVotes = new ArrayList<>();
        for (Long teamId : teamIds) {
            Vote findResult = voteRepository.findInCompleteVoteByMemberAndTeam(null, null);
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
