package ssafy.lambda.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.lambda.vote.entity.VoteInfo;

import java.util.Optional;

public interface VoteInfoRepository extends JpaRepository<VoteInfo, Long> {

    /* 해당 투표를 유저가 이미 투표한 적이 있는지 판단하는 함수
    SELECT COALESCE(1, 0) AS vote_exists
    FROM voteinfo
    WHERE voteid = {voteId} AND memberid = {memberId}
    LIMIT 1;
    */
    public boolean existsByVoteIdAndMemberId(Long voteId, Long memberId);

}
