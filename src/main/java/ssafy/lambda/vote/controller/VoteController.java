package ssafy.lambda.vote.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ssafy.lambda.vote.dto.RequestMemberDto;
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
            @RequestBody RequestVoteDto requestVoteDto
            ){
        log.info(requestVoteDto.toString());
        voteService.createVote(requestVoteDto);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "투표하기", description = "유저가 투표를 합니다") //, 유저는 투표를 하고, 현재 결과를 반환받습니다")
    @GetMapping("/vote/{voteId}")
    public ResponseEntity createVote(
            @PathVariable Long voteId,
            @RequestParam Long memberId,
            @RequestParam Long choosedMemberId,
            @RequestParam Long teamId
    ){
        log.info("team {}, vote {} : {} -> {}", teamId, voteId, memberId, choosedMemberId );
        voteService.doVote(voteId, teamId, memberId, choosedMemberId);
        return ResponseEntity.ok().build();
    }
}
