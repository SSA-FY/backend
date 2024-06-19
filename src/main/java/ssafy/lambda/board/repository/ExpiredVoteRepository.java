package ssafy.lambda.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.lambda.board.entity.ExpiredVote;

public interface ExpiredVoteRepository extends JpaRepository<ExpiredVote, Long> {
}
