package ssafy.lambda.vote.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.repository.MemberRepository;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.dto.ResponseProfileWithPercentDto;
import ssafy.lambda.vote.dto.ResponseVoteDto;
import ssafy.lambda.vote.entity.Vote;
import ssafy.lambda.vote.entity.VoteInfo;
import ssafy.lambda.vote.repository.VoteInfoRepository;
import ssafy.lambda.vote.repository.VoteRepository;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService{

    private final VoteRepository voteRepository;
    private final VoteInfoRepository voteInfoRepository;
    // TODO memberRepository 분리하기
    private final MemberRepository memberRepository;
    // TODO: CommonService 받아서 doVote(), getUserVote() 메서드 완성
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createVote(Long memberId, Long teamId, RequestVoteDto requestVoteDto) {

        // Entity 수정 후, 멤버십을 연결한 vote 객체 생성

        Vote vote = Vote.builder()
                .content(requestVoteDto.getContent())
                .imgUrl(requestVoteDto.getBackgroundUrl())
                .build();
        voteRepository.save(vote);

    }

    @Override
    public void doVote(Long voteId, Long teamId, Long memberId, Long choosedMemberId) throws IllegalArgumentException{

        Vote foundVote = validateVote(voteId);

        // 이미 투표했는가
        if(voteInfoRepository.existsByVoteAndMemberId(foundVote, memberId)){
            throw new IllegalArgumentException("The member already voted");
        }

        //TODO : 이후, Member 도메인으로부터 member를 받아서 로직 처리
        // 투표하기
        VoteInfo voteInfo
                = VoteInfo.builder()
                    .choosedMember(null)
                    .member(null)
                    .vote(foundVote)
                .build();

        voteInfoRepository.save(voteInfo);

        return;
    }

    @Override
    public void review(Long memberId, Long voteId, String review){

        Vote foundVote = validateVote(voteId);

        // 투표했는가
        VoteInfo foundVoteInfo = voteInfoRepository.findByVoteAndMemberId(foundVote, memberId).orElseThrow(
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
        List<ResponseProfileWithPercentDto> resultOfDto = new ArrayList<>();
        for(Object[] ob : resultOfQuery){
            Long memberId = ((Number) ob[0]).longValue();
//            Long cnt = ((Number) ob[1]).longValue();
            Double percent = ((Number) ob[2]).doubleValue();

            // 투표받은사람이 존재하지 않음(그 사이 회원탈퇴함)
            Member choosedMember = memberRepository.findById(memberId).orElseThrow(
                ()-> new IllegalArgumentException("user doesn't exist"));

            var dto = new ResponseProfileWithPercentDto(
                choosedMember.getName(),
                choosedMember.getProfileImgUrl(),
                percent
            );
            resultOfDto.add(dto);
        }

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
    @Transactional
    public List<ResponseVoteDto> getUserVote(Long memberId, Long teamId) {
        //TODO : MemberService, TeamService or MemberShipService에서 id로 엔티티 찾아오기
        return voteRepository.findVoteByMemberIdAndTeamId(null, null);
    }
}
