package ssafy.lambda.vote.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.vote.entity.Vote;
import ssafy.lambda.vote.entity.VoteInfo;

public interface VoteInfoRepository extends JpaRepository<VoteInfo, Long> {

    /* 해당 투표를 멤버가 이미 투표한 적이 있는지 판단하는 함수
    SELECT COALESCE(1, 0) AS vote_exists
    FROM voteinfo
    WHERE voteid = {vote} AND voterId = {voterId}
    LIMIT 1;
    * */
    public boolean existsByVoteAndVoter(Vote vote, Member voter);

    /* 멤버가 투표한 내용을 가져오는 함수
    SELCT *
    FROM voteinfo
    WHERE voteid = {voteId} AND voterId = {voterId}
    * */
    public Optional<VoteInfo> findByVoteAndVoter(Vote vote, Member voter);


}
