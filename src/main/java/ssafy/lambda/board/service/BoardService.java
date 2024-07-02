package ssafy.lambda.board.service;

import java.util.List;
import java.util.UUID;
import ssafy.lambda.board.dto.ResponseBoardDetailDto;
import ssafy.lambda.board.dto.ResponseBoardSummaryDto;
import ssafy.lambda.board.dto.ResponseCommentDto;

public interface BoardService {

    List<ResponseBoardSummaryDto> getBoardList(String teamName, UUID memberId, Long page);

    ResponseBoardDetailDto getBoardDetail(Long expiredVoteId, Long page);

    List<ResponseCommentDto> getCommentList(Long expiredVoteId, Long page);

    void writeComment(Long expiredVoteId, UUID memberId, String content);

    void editComment(Long commentId, UUID memberId, String content);

    void deleteComment(Long commentId, UUID memberId);

}
