package ssafy.lambda.vote.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.lambda.vote.entity.VoteComment;

public interface CommentRepository extends JpaRepository<VoteComment, Long> {

    @Query("SELECT c.id as commentId, m.name as writer, c.content, c.createAt as time "
        + "FROM VoteComment c "
        + "JOIN Member m ON c.member.memberId = m.memberId "
        + "WHERE c.expiredVote.id = :expiredVoteId")
    List<Object[]> findAllByVoteId(@Param("expiredVoteId") Long expiredVoteId);
}
