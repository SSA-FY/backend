package ssafy.lambda.board.service;

import java.util.List;
import ssafy.lambda.board.dto.ResponseCommentDto;

public interface BoardService {

    List<ResponseCommentDto> getCommentList(Long expiredVoteId);

    void writeComment(Long expiredVoteId, Long memberId, String content);

    void editComment(Long commentId, Long memberId, String content);

    void deleteComment(Long commentId);

}
