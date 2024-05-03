package ssafy.lambda.board.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.lambda.board.dto.ResponseCommentDto;
import ssafy.lambda.board.entity.BoardComment;

public interface CommentRepository extends JpaRepository<BoardComment, Long> {

    @Query(
        "SELECT new ssafy.lambda.board.dto.ResponseCommentDto(c.id,m.name, c.content, c.createdAt) "
            + "FROM BoardComment c "
            + "JOIN Member m ON c.commenter.memberId = m.memberId "
            + "WHERE c.expiredVote.id = :expiredVoteId")
    List<ResponseCommentDto> findAllByVoteId(@Param("expiredVoteId") Long expiredVoteId);
}
