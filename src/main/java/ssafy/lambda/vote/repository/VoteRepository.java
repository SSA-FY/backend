package ssafy.lambda.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.lambda.vote.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

}
