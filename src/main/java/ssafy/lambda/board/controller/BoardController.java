package ssafy.lambda.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.board.dto.ResponseCommentDto;
import ssafy.lambda.board.service.BoardService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "댓글 조회", description = "만료된 투표에 대한 댓글 목록을 반환합니다")
    @GetMapping("/comment")
    public ResponseEntity<List<ResponseCommentDto>> getComments(
        @RequestParam Long voteId
    ) {
        log.info("getVoteResult - vote {} ", voteId);
        List<ResponseCommentDto> voteResult = boardService.getComments(voteId);
        return ResponseEntity.ok()
                             .body(voteResult);
    }

    @Operation(summary = "댓글 작성", description = "만료된 투표에 대한 댓글을 작성합니다")
    @PostMapping("/comment")
    public ResponseEntity writeComment(
        @RequestParam Long voteId,
        @RequestParam Long memberId,
        @RequestBody String content
    ) {
        log.info("writeComment - member {}, vote {}  : {}", memberId, voteId, content);
        boardService.writeComment(voteId, memberId, content);
        return ResponseEntity.ok()
                             .build();
    }


    @Operation(summary = "댓글 삭제", description = "작성한 댓글을 삭제합니다")
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity deleteComment(
        @PathVariable Long commentId,
        @RequestParam Long memberId
    ) {
        log.info("deleteComment - member {}, comment {}  : {}", memberId, commentId);
        boardService.deleteComment(commentId);
        return ResponseEntity.ok()
                             .build();
    }
}

