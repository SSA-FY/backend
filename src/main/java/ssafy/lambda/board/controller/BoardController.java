package ssafy.lambda.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.board.dto.ResponseBoardDetailDto;
import ssafy.lambda.board.dto.ResponseBoardSummaryDto;
import ssafy.lambda.board.dto.ResponseCommentDto;
import ssafy.lambda.board.service.BoardService;

@SecurityRequirement(name = "token")
@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "보드 리스트 조회", description = "그룹 별 만료된 투표 목록을 반환합니다<br>페이지 별 10개의 보드를 반환합니다")
    @GetMapping("/boards/{teamId}")
    public ResponseEntity<List<ResponseBoardSummaryDto>> getBoards(
        Authentication authentication,
        @PathVariable(name = "teamId") Long teamId,
        @RequestParam(defaultValue = "0", name = "page") Long page
    ) {
        UUID memberId = UUID.fromString(authentication.getName());
        List boardList = boardService.getBoardList(teamId, memberId, page);
        return ResponseEntity.ok()
                             .body(boardList);
    }

    @Operation(summary = "보드 상세 조회", description = "만료된 투표 목록의 결과를 반환합니다<br>페이지 별 6명의 결과를 반환합니다")
    @GetMapping("/board/{boardId}")
    public ResponseEntity<ResponseBoardDetailDto> getBoards(
        @PathVariable(name = "boardId") Long boardId,
        @RequestParam(defaultValue = "0", name = "page") Long page
    ) {
        ResponseBoardDetailDto boardDetail = boardService.getBoardDetail(boardId, page);
        return ResponseEntity.ok()
                             .body(boardDetail);
    }

    //////////////////////////////////////////////


    @Operation(summary = "댓글 조회", description = "만료된 투표(보드)에 대한 댓글 목록을 반환합니다<br>페이지 별 20개의 댓글을 반환합니다")
    @GetMapping("/comment/{boardId}")
    public ResponseEntity<List<ResponseCommentDto>> getComments(
        @PathVariable(name = "boardId") Long boardId,
        @RequestParam(defaultValue = "0", name = "page") Long page
    ) {
//        log.info("getCommentList - vote {} ", boardId);
        List<ResponseCommentDto> commentList = boardService.getCommentList(boardId, page);
        return ResponseEntity.ok()
                             .body(commentList);
    }

    @Operation(summary = "댓글 작성", description = "만료된 투표(보드)에 대한 댓글을 작성합니다")
    @PostMapping("/comment/{boardId}")
    public ResponseEntity writeComment(
        Authentication authentication,
        @PathVariable(name = "boardId") Long boardId,
        @RequestBody String content
    ) {
        UUID memberId = UUID.fromString(authentication.getName());
//        log.info("writeComment - member {}, vote {}  : {}", memberId, boardId, content);
        boardService.writeComment(boardId, memberId, content);
        return ResponseEntity.ok()
                             .build();
    }

    @Operation(summary = "댓글 수정", description = "작성한 댓글을 수정합니다")
    @PutMapping("/comment/{commentId}")
    public ResponseEntity editComment(
        Authentication authentication,
        @PathVariable(name = "commentId") Long commentId,
        @RequestBody String content
    ) {
        UUID memberId = UUID.fromString(authentication.getName());
//        log.info("editComment - member {}, comment {}  : {}", memberId, commentId, content);
        boardService.editComment(commentId, memberId, content);
        return ResponseEntity.ok()
                             .build();
    }

    @Operation(summary = "댓글 삭제", description = "작성한 댓글을 삭제합니다")
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity deleteComment(
        Authentication authentication,
        @PathVariable(name = "commentId") Long commentId
    ) {
        UUID memberId = UUID.fromString(authentication.getName());
//        log.info("deleteComment - member {}, comment {}  : {}", memberId, commentId);
        boardService.deleteComment(commentId, memberId);
        return ResponseEntity.ok()
                             .build();
    }
}

