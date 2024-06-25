package ssafy.lambda.board.repository;

import java.util.List;

public interface BoardRepositoryCustom {

    List<Object[]> findTopMemberByBoard(Long expiredVoteId);

    Long getCntByBoard(Long expiredVoteId);

    List<Object[]> findMemberCntByBoard(Long expiredVoteId, Long page);
}
