package ssafy.lambda.board.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.lambda.board.entity.BoardComment;
import ssafy.lambda.board.entity.ExpiredVote;

public interface CommentRepository extends JpaRepository<BoardComment, Long> {

    List<BoardComment> findAllByExpiredVote(ExpiredVote expiredVote, Pageable pageable);
}
