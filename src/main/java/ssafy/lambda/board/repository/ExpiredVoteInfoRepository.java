package ssafy.lambda.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.lambda.board.entity.ExpiredVoteInfo;

public interface ExpiredVoteInfoRepository extends JpaRepository<ExpiredVoteInfo, Long>, ExpiredVoteInfoRepositoryCustom {

}
