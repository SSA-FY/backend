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
import ssafy.lambda.member.dto.RequestMemberDto;
import ssafy.lambda.member.dto.ResponseMemberDto;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.service.MemberService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "유저 목록 조회", description = "유저 목록을 조회합니다")
    @GetMapping
    public ResponseEntity<List<ResponseMemberDto>> getMembers() {
        List<ResponseMemberDto> members = memberService.findAllMember()
            .stream()
            .map(ResponseMemberDto::new)
            .toList();

        return ResponseEntity.status(HttpStatus.OK)
            .body(members);
    }

    @Operation(summary = "유저 조회", description = "유저 정보를 조회합니다")
    @GetMapping("{id}")
    public ResponseEntity<ResponseMemberDto> getMember(@PathVariable("id") Long id) {
        Member member = memberService.findMemberById(id);

        return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseMemberDto(member));
    }

    @Operation(summary = "유저 생성", description = "새로운 유저를 생성합니다")
    @PostMapping
    public ResponseEntity<ResponseMemberDto> register(
        @RequestBody RequestMemberDto requestMemberDto) {
        Member savedMember = memberService.createMember(requestMemberDto);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ResponseMemberDto(savedMember));
    }

    @Operation(summary = "유저 갱신", description = "유저 정보를 갱신합니다")
    @PutMapping("{id}")
    public ResponseEntity<ResponseMemberDto> update(@PathVariable("id") Long id,
        @RequestBody RequestMemberDto requestMemberDto) {
        Member updatedMember = memberService.updateMember(id, requestMemberDto);

        return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseMemberDto(updatedMember));
    }

    @Operation(summary = "유저 삭제", description = "유저를 삭제합니다")
    @DeleteMapping("{id}")
    public ResponseEntity<ResponseMemberDto> delete(@PathVariable("id") Long id) {
        memberService.deleteMemberById(id);

        return ResponseEntity.status(HttpStatus.OK)
            .build();
    }
}
