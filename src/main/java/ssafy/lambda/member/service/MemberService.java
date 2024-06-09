package ssafy.lambda.member.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.lambda.member.dto.RequestMemberDto;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.entity.SocialType;
import ssafy.lambda.member.exception.MemberNotFoundException;
import ssafy.lambda.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(RequestMemberDto requestMemberDto) {
        Member member = requestMemberDto.toEntity();

        return memberRepository.save(member);
    }

    public Member findMemberById(UUID memberId) {
        return memberRepository.findById(memberId)
                               .orElseThrow(() -> new MemberNotFoundException(memberId));

    }

    @Transactional
    public Member updateMember(UUID memberId, RequestMemberDto requestMemberDto) {
        Member member = requestMemberDto.toEntity();

//        Member updatedMember = memberRepository.findById(memberId)
//            .orElseThrow(() -> new MemberNotFoundException(memberId));
//
//        updatedMember.update(member.getName(), member.getPoint(), member.getProfileImgUrl());

        return member;
    }

    public void deleteMemberById(UUID memberId) {
        memberRepository.deleteById(memberId);
    }

    @Transactional
    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    public Member findMemberByEmailAndSocial(String email, SocialType social) {
        return memberRepository.findByEmailAndSocial(email, social)
                               .orElseThrow(() -> new MemberNotFoundException(email));
    }

    public List<Member> findAllMemberByTeamId(Long teamId) {
        return memberRepository.findAllByTeamId(teamId);
    }
}
