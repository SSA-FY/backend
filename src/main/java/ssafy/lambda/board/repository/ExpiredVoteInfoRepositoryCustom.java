package ssafy.lambda.board.repository;


import java.util.List;
import ssafy.lambda.board.dto.ExpiredVoteWrapper;

public interface ExpiredVoteInfoRepositoryCustom {
    List<ExpiredVoteWrapper> findByExpiredVoteIsNull();

}
