package ssafy.lambda.board.service;

import java.util.List;
import ssafy.lambda.board.dto.ResponseCommentDto;

public interface BoardService {

    List<ResponseCommentDto> getComments(Long expriedVoteId);

    void writeComment(Long voteId, Long memberId, String content);

    void editComment(Long commentId, Long memberId, String content);

    void deleteComment(Long commentId);

}
