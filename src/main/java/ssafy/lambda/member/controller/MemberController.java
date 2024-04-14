package ssafy.lambda.member.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.service.MemberService;

@AllArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public void register(@RequestBody Member member) {
        memberService.createMember(member);
    }

    @GetMapping
    public List<Member> getMembers() {
        return memberService.findAllMember();
    }

    @GetMapping("{id}")
    public Member getMember(@PathVariable("id") Long id) {
        return memberService.findMemberById(id);
    }

    @PutMapping
    public void update(@RequestBody Member member) {
        memberService.updateMember(member);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        memberService.deleteMemberById(id);
    }
}
