package ssafy.lambda.member.controller;

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

    @GetMapping
    public ResponseEntity<List<ResponseMemberDto>> getMembers() {
        List<ResponseMemberDto> members = memberService.findAllMember()
            .stream()
            .map(ResponseMemberDto::new)
            .toList();

        return ResponseEntity.status(HttpStatus.OK)
            .body(members);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseMemberDto> getMember(@PathVariable("id") Long id) {
        Member member = memberService.findMemberById(id);

        return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseMemberDto(member));
    }

    @PostMapping
    public ResponseEntity<ResponseMemberDto> register(
        @RequestBody RequestMemberDto requestMemberDto) {
        Member savedMember = memberService.createMember(requestMemberDto);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ResponseMemberDto(savedMember));
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseMemberDto> update(@PathVariable("id") Long id,
        @RequestBody RequestMemberDto requestMemberDto) {
        Member updatedMember = memberService.updateMember(id, requestMemberDto);

        return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseMemberDto(updatedMember));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseMemberDto> delete(@PathVariable("id") Long id) {
        memberService.deleteMemberById(id);

        return ResponseEntity.status(HttpStatus.OK)
            .build();
    }
}
