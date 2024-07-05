package ssafy.lambda.vote.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import ssafy.lambda.global.annotation.ApiErrorResponse;
import ssafy.lambda.global.response.ApiError;
import ssafy.lambda.vote.dto.RequestReviewDto;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.dto.ResponseProfileWithPercentDto;
import ssafy.lambda.vote.dto.ResponseTodayVoteInfoDto;
import ssafy.lambda.vote.dto.ResponseVoteDto;
import ssafy.lambda.vote.dto.ResponseVoteWithVoteInfoListDto;
import ssafy.lambda.vote.service.VoteService;

@SecurityRequirement(name = "token")
@RestController
@RequiredArgsConstructor
@Slf4j
public class VoteController {

    private final VoteService voteService;

    @Operation(summary = "투표 생성", description = "멤버가 새로운 투표를 만듭니다")
    @PostMapping("/vote")
    public ResponseEntity createVote(
        Authentication authentication,
        @RequestParam(name = "teamName") String teamName,
        @RequestPart(name = "content") RequestVoteDto requestVoteDto,
        @RequestPart(name = "img", required = false) MultipartFile img
    ) {
        UUID memberId = UUID.fromString(authentication.getName());
//        log.info("createVote - team {}, memberId {} : {}, img : {}", teamName, memberId,
//            requestVoteDto.toString(), img);
        voteService.createVote(memberId, teamName, requestVoteDto, img);
        return ResponseEntity.ok()
                             .build();
    }


    @Operation(summary = "투표하기", description = "멤버가 투표를 합니다")
    @PostMapping("/vote/{voteId}")
    public ResponseEntity createVote(
        Authentication authentication,
        @PathVariable(name = "voteId") Long voteId,
        @RequestParam(name = "voteeTag") String voteeTag
    ) {
        UUID voterId = UUID.fromString(authentication.getName());
//        log.info("doVote - vote {} : {} -> {}", voteId, voterId, voteeTag);
        Long voteInfoId = voteService.doVote(voteId, voterId, voteeTag);
//        log.info("voteInfoId = {}", voteInfoId);
        URI uri = UriComponentsBuilder.fromPath(String.valueOf(voteInfoId))
                                      .buildAndExpand()
                                      .toUri();
        return ResponseEntity.created(uri)
                             .build();
    }

    @Operation(summary = "한줄평 남기기", description = "멤버가 투표한 멤버에게 한줄평를 남깁니다")
    @PostMapping("/vote/review/{voteInfoId}")
    public ResponseEntity createReview(
        Authentication authentication,
        @PathVariable(name = "voteInfoId") Long voteInfoId,
        @RequestBody RequestReviewDto requestReviewDto
    ) {
        UUID memberId = UUID.fromString(authentication.getName());

//        log.info("review - member {}, voteInfoId {}. voteId {} : {}", memberId, voteInfoId,
//            requestReviewDto.getVoteId(), requestReviewDto.getReview());
        voteService.review(memberId, voteInfoId, requestReviewDto);
        return ResponseEntity.ok()
                             .build();
    }


    @Operation(summary = "투표 결과", description = "현재 투표의 결과로, 상위 6명 멤버 정보를 반환합니다")
    @GetMapping("/vote/{voteId}")
    public ResponseEntity<List<ResponseProfileWithPercentDto>> getVoteResult(
        @PathVariable(name = "voteId") Long voteId
    ) {
//        log.info("voteResult - vote {}");
        List<ResponseProfileWithPercentDto> voteResult = voteService.voteResult(voteId);
        return ResponseEntity.ok()
                             .body(voteResult);
    }

    @Operation(summary = "투표 리스트 가져오기", description = "멤버가 선택한 팀의 진행 중인 투표를 가져옵니다.")
    @GetMapping("/vote/list")
    public ResponseEntity<List<ResponseVoteDto>> getVoteList(
        Authentication authentication,
        @RequestParam(name = "teamName") String teamName
    ) {
        UUID memberId = UUID.fromString(authentication.getName());

        List<ResponseVoteDto> responseVoteDtoList = voteService.getVoteListByMember(memberId,
            teamName);
//        log.info("member {}, team {} -> ListCount : {}", memberId, teamName,
//            responseVoteDtoList.size());

        return ResponseEntity.ok()
                             .body(responseVoteDtoList);
    }

    @Operation(summary = "투표 정보 열기", description = "멤버가 선택한 투표 정보의 투표자 정보를 Open 합니다.")
    @ApiErrorResponse({ApiError.VoteInfoNotFoundException, ApiError.NotEnoughPointException,
        ApiError.UnauthorizedMember})
    @PutMapping("/voteinfo/open/{voteInfoId}")
    public ResponseEntity<HttpStatus> openVoteInfo(
        Authentication authentication,
        @PathVariable(name = "voteInfoId") Long voteInfoId
    ) {
        UUID memberId = UUID.fromString(authentication.getName());

//        log.info("member {} -> Open : (voteInfoId {})", memberId, voteInfoId);
        voteService.openVoteInfo(memberId, voteInfoId);
        return ResponseEntity.ok()
                             .build();
    }

    @Operation(summary = "나에게 투표한 정보 리스트 반환", description = "해당 투표에서 나에게 투표한 투표 정보들을 반환합니다. ")
    @ApiErrorResponse({ApiError.VoteNotFoundException})
    @GetMapping("/voteinfo/list/{voteId}")
    public ResponseEntity<ResponseVoteWithVoteInfoListDto> getVoteInfoToMeList(
        Authentication authentication,
        @PathVariable(name = "voteId") Long voteId
    ) {
        UUID memberId = UUID.fromString(authentication.getName());

//        log.info("member {}, voteId : {}", memberId, voteId);
        ResponseVoteWithVoteInfoListDto responseVoteWithVoteInfoList = voteService.getVoteInfoToMeList(
            memberId, voteId);

        return ResponseEntity.ok()
                             .body(responseVoteWithVoteInfoList);
    }

    @Operation(summary = "오늘의 투표 개수, 한줄 평 개수, 포인트 확인", description = "오늘의 투표 개수, 한줄 평 개수, 포인트 확인")
    @GetMapping("/voteinfo/today")
    public ResponseEntity<ResponseTodayVoteInfoDto> getTodayVoteInfo(
        Authentication authentication
    ) {
        UUID memberId = UUID.fromString(authentication.getName());

        ResponseTodayVoteInfoDto todayVoteInfo = voteService.getTodayVoteInfo(memberId);

        return ResponseEntity.ok()
                             .body(todayVoteInfo);
    }
}
