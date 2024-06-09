package ssafy.lambda.vote.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.dto.ResponseProfileWithPercentDto;
import ssafy.lambda.vote.dto.ResponseVoteDto;
import ssafy.lambda.vote.dto.ResponseVoteStatusDto;
import ssafy.lambda.vote.service.VoteService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VoteController {

    private final VoteService voteService;

    @Operation(summary = "투표 생성", description = "멤버가 새로운 투표를 만듭니다")
    @PostMapping("/vote")
    public ResponseEntity createVote(
        @RequestParam Long memberId,
        @RequestParam Long teamId,
        @RequestBody RequestVoteDto requestVoteDto
    ) {
        log.info("createVote - team {}, memberId {} : {}", teamId, memberId,
            requestVoteDto.toString());
        voteService.createVote(memberId, teamId, requestVoteDto);
        return ResponseEntity.ok()
                             .build();
    }


    @Operation(summary = "투표하기", description = "멤버가 투표를 합니다")
    @PostMapping("/vote/{voteId}")
    public ResponseEntity createVote(
        @PathVariable Long voteId,
        @RequestParam Long voterId,
        @RequestParam Long voteeId,
        @RequestParam Long teamId
    ) {
        log.info("doVote - team {}, vote {} : {} -> {}", teamId, voteId, voterId, voteeId);
        voteService.doVote(voteId, teamId, voterId, voteeId);
        return ResponseEntity.ok()
                             .build();
    }

    @Operation(summary = "한줄평 남기기", description = "멤버가 투표한 멤버에게 한줄평를 남깁니다")
    @PostMapping("/vote/review/{voteId}")
    public ResponseEntity createReview(
        @PathVariable Long voteInfoId,
        @RequestParam Long memberId,
        @RequestBody String review
    ) {
        log.info("review - member {}, vote {}  : {}", memberId, voteInfoId, review);
        voteService.review(memberId, voteInfoId, review);
        return ResponseEntity.ok()
                             .build();
    }


    @Operation(summary = "투표 결과", description = "현재 투표의 결과로, 상위 6명 멤버 정보를 반환합니다")
    @GetMapping("/vote/{voteId}")
    public ResponseEntity<List<ResponseProfileWithPercentDto>> getVoteResult(
        @PathVariable Long voteId
    ) {
        log.info("voteResult - vote {} ");
        List<ResponseProfileWithPercentDto> voteResult = voteService.voteResult(voteId);
        return ResponseEntity.ok()
                             .body(voteResult);
    }

    @Operation(summary = "투표 리스트 가져오기", description = "멤버가 선택한 팀의 진행 중인 투표를 가져옵니다.")
    @GetMapping("/vote/list")
    public ResponseEntity<List<ResponseVoteDto>> getVoteList(
        @RequestParam Long memberId,
        @RequestParam Long teamId
    ) {
        List<ResponseVoteDto> responseVoteDtoList = voteService.getVoteListByMember(memberId,
            teamId);
        log.info("member {}, team {} -> ListCount : {}", memberId, teamId,
            responseVoteDtoList.size());

        return ResponseEntity.ok()
                             .body(responseVoteDtoList);
    }

    @GetMapping("/vote/sortList")
    public ResponseEntity<ResponseVoteStatusDto> teamSortList(
        @RequestParam Long memberId,
        @RequestParam Long teamId
    ) {
        ResponseVoteStatusDto responseVoteStatusDto = voteService.sortByVoteStatus(null, null);

        return ResponseEntity.ok()
                             .body(responseVoteStatusDto);
    }

}
