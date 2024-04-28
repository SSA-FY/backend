package ssafy.lambda.member.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.lambda.member.dto.RequestMemberDto;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.entity.SocialType;
import ssafy.lambda.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(RequestMemberDto requestMemberDto) {
        Member member = requestMemberDto.toEntity();

        return memberRepository.save(member);
    }

    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
            .orElse(null);
    }

    @Transactional
    public Member updateMember(Long id, RequestMemberDto requestMemberDto) {
        Member member = requestMemberDto.toEntity();

        Member updatedMember = memberRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        updatedMember.update(member.getName(), member.getPoint(), member.getProfileImgUrl());

        return updatedMember;
    }

    public void deleteMemberById(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    public Member findMemberByEmailAndSocial(String id, SocialType social) {
        return memberRepository.findByEmailAndSocial(id, social)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }
}
