package ssafy.lambda.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ssafy.lambda.global.annotation.ApiErrorResponse;
import ssafy.lambda.global.response.ApiError;
import ssafy.lambda.global.response.dto.Response;
import ssafy.lambda.global.response.dto.ResponseData;
import ssafy.lambda.member.dto.RequestMemberUpdateDto;
import ssafy.lambda.member.dto.ResponseMemberDto;
import ssafy.lambda.member.dto.ResponseMemberUpdateDto;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.service.MemberService;

@SecurityRequirement(name = "token")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

//    @Operation(summary = "멤버 목록 조회", description = "멤버 목록을 조회합니다")
//    @GetMapping
//    public ResponseEntity<List<ResponseMemberDto>> getMembers() {
//        List<ResponseMemberDto> members = memberService.findAllMember()
//                                                       .stream()
//                                                       .map(ResponseMemberDto::new)
//                                                       .toList();
//        return ResponseData.res(HttpStatus.CREATED, "멤버 리스트 반환", members);
//    }

    @Operation(summary = "멤버 조회", description = "멤버 정보를 조회합니다")
    @ApiErrorResponse({ApiError.MemberNotFound})
    @GetMapping
    public ResponseEntity<ResponseMemberDto> getMember(Authentication authentication) {
        UUID memberId = UUID.fromString(authentication.getName());
        Member member = memberService.findMemberById(memberId);
        return ResponseData.res(HttpStatus.OK, "멤버 정보 조회", new ResponseMemberDto(member));
    }

//    @Operation(summary = "멤버 등록", description = "새로운 멤버를 등록합니다")
//    @ApiErrorResponse({ApiError.MemberNotFound,
//        ApiError.ExampleCreated})
//    @PostMapping
//    public ResponseEntity<ResponseMemberDto> register(
//        @RequestBody RequestMemberDto requestMemberDto) {
//        Member savedMember = memberService.createMember(requestMemberDto);
//        return Response.res(HttpStatus.CREATED, "멤버 등록 성공");
//    }

    @Operation(summary = "Tag 조회", description = "Tag 정보를 조회합니다")
    @ApiErrorResponse({ApiError.MemberNotFound})
    @GetMapping("{tag}")
    public ResponseEntity<Boolean> checkTag(@PathVariable("tag") String tag) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(memberService.existsMemberByTag(tag));
    }

    @Operation(summary = "멤버 갱신", description = "멤버 정보를 갱신합니다")
    @ApiErrorResponse({ApiError.MemberNotFound})
    @PutMapping
    public ResponseEntity<ResponseMemberUpdateDto> update(Authentication authentication,
        @RequestPart("dto") RequestMemberUpdateDto requestMemberUpdateDto,
        @RequestPart(value = "img", required = false) MultipartFile img) {

        UUID memberId = UUID.fromString(authentication.getName());

        Member updatedMember = memberService.updateMember(memberId,
            requestMemberUpdateDto, img);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(ResponseMemberUpdateDto.builder()
                                                          .name(updatedMember.getName())
                                                          .tag(updatedMember.getTag())
                                                          .profileImgUrl(
                                                              updatedMember.getProfileImgUrl())
                                                          .build());
    }

    @Operation(summary = "멤버 삭제", description = "멤버를 삭제합니다")
    @ApiErrorResponse({ApiError.MemberNotFound})
    @DeleteMapping
    public ResponseEntity<ResponseMemberDto> delete(Authentication authentication) {
        UUID memberId = UUID.fromString(authentication.getName());
        memberService.deleteMemberById(memberId);
        return Response.res(HttpStatus.OK, "멤버 삭제 성공");
    }
}
