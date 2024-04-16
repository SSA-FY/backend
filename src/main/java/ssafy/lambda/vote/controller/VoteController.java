package ssafy.lambda.vote.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.repository.VoteRepository;
import ssafy.lambda.vote.service.VoteService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VoteController {

    // @Autowired
    private final VoteService voteService;

    @Operation(summary = "투표 생성", description = "유저가 새로운 투표를 만듭니다")
    @PostMapping("/vote")
    public ResponseEntity createVote(
        @RequestParam Long memberId,
        @RequestParam Long teamId,
        @RequestBody RequestVoteDto requestVoteDto
    ){
        log.info("createVote - team {}, memberId {} : {}", teamId, memberId, requestVoteDto.toString());
        voteService.createVote(memberId, teamId, requestVoteDto);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "투표하기", description = "유저가 투표를 합니다")
    @GetMapping("/vote/{voteId}")
    public ResponseEntity createVote(
            @PathVariable Long voteId,
            @RequestParam Long memberId,
            @RequestParam Long choosedMemberId,
            @RequestParam Long teamId
    ){
        log.info("doVote - team {}, vote {} : {} -> {}", teamId, voteId, memberId, choosedMemberId );
        voteService.doVote(voteId, teamId, memberId, choosedMemberId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "한줄평 남기기", description = "유저가 투표한 유저에게 한줄평를 남깁니다")
    @PostMapping("/vote/review/{voteId}")
    public ResponseEntity createReview(
        @PathVariable Long voteId,
        @RequestParam Long memberId,
        @RequestBody String review
    ){
        log.info("review - member {}, vote {}  : {}", memberId, voteId, review );
        voteService.review(memberId, voteId, review);
        return ResponseEntity.ok().build();
    }
}
