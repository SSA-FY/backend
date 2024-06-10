package ssafy.lambda.board.service;

import java.util.List;
import java.util.UUID;
import ssafy.lambda.board.dto.ResponseCommentDto;

public interface BoardService {

    List<ResponseCommentDto> getCommentList(Long expiredVoteId);

    void writeComment(Long expiredVoteId, UUID memberId, String content);

    void editComment(Long commentId, UUID memberId, String content);

    void deleteComment(Long commentId);

}
