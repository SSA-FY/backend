package ssafy.lambda.board.repository;

import ssafy.lambda.board.entity.VoteComment;

// board 관련 쿼리 작성
public interface BoardRepositoryCustom {

    void saveComment(VoteComment voteComment);
}
