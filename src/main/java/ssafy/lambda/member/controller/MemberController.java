package ssafy.lambda.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.global.annotation.ApiErrorResponse;
import ssafy.lambda.global.response.ApiError;
import ssafy.lambda.global.response.dto.Response;
import ssafy.lambda.global.response.dto.ResponseData;
import ssafy.lambda.member.dto.RequestMemberDto;
import ssafy.lambda.member.dto.ResponseMemberDto;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.service.MemberService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "멤버 목록 조회", description = "멤버 목록을 조회합니다")
    @GetMapping
    public ResponseEntity<List<ResponseMemberDto>> getMembers() {
        List<ResponseMemberDto> members = memberService.findAllMember()
                                                       .stream()
                                                       .map(ResponseMemberDto::new)
                                                       .toList();
        return ResponseData.res(HttpStatus.CREATED, "멤버 리스트 반환", members);
    }

    @Operation(summary = "멤버 조회", description = "멤버 정보를 조회합니다")
    @ApiErrorResponse({ApiError.MemberNotFound})
    @GetMapping("{id}")
    public ResponseEntity<ResponseMemberDto> getMember(@PathVariable("id") Long id) {
        Member member = memberService.findMemberById(id);
        return ResponseData.res(HttpStatus.OK, "멤버 정보 조회", new ResponseMemberDto(member));
    }

    @Operation(summary = "멤버 등록", description = "새로운 멤버를 등록합니다")
    @ApiErrorResponse({ApiError.MemberNotFound,
        ApiError.ExampleCreated})
    @PostMapping
    public ResponseEntity<ResponseMemberDto> register(
        @RequestBody RequestMemberDto requestMemberDto) {
        Member savedMember = memberService.createMember(requestMemberDto);
        return Response.res(HttpStatus.CREATED, "멤버 등록 성공");
    }

    @Operation(summary = "멤버 갱신", description = "멤버 정보를 갱신합니다")
    @ApiErrorResponse({ApiError.MemberNotFound})
    @PutMapping("{id}")
    public ResponseEntity<ResponseMemberDto> update(@PathVariable("id") Long id,
        @RequestBody RequestMemberDto requestMemberDto) {
        Member updatedMember = memberService.updateMember(id, requestMemberDto);
        return Response.res(HttpStatus.OK, "멤버 정보 갱신 성공");
    }

    @Operation(summary = "멤버 삭제", description = "멤버를 삭제합니다")
    @ApiErrorResponse({ApiError.MemberNotFound})
    @DeleteMapping("{id}")
    public ResponseEntity<ResponseMemberDto> delete(@PathVariable("id") Long id) {
        memberService.deleteMemberById(id);
        return Response.res(HttpStatus.OK, "멤버 삭제 성공");
    }
}
