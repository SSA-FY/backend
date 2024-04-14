package ssafy.lambda.membership.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.repository.MemberRepository;
import ssafy.lambda.membership.dto.RequestMembershipDto;
import ssafy.lambda.membership.entity.Membership;
import ssafy.lambda.membership.repository.MembershipRepository;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.team.repository.TeamRepository;

@RequiredArgsConstructor
@Service
public class MembershipService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final MembershipRepository membershipRepository;

    @Transactional
    public Membership createMembership(RequestMembershipDto requestMembershipDto) {
        Member member = memberRepository.findById(requestMembershipDto.getMemberId())
            .orElseThrow(() -> new IllegalArgumentException(
                "Member not found with id " + requestMembershipDto.getMemberId()));
        Team team = teamRepository.findById(requestMembershipDto.getTeamId())
            .orElseThrow(
                () -> new IllegalArgumentException(
                    "Team not found with id " + requestMembershipDto.getTeamId()));

        return membershipRepository.save(Membership.builder()
            .member(member)
            .team(team)
            .build());
    }

    @Transactional
    public List<Membership> findMembershipByTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Team not found with id " + teamId));

        return membershipRepository.findByTeam(team);
    }

    @Transactional
    public List<Membership> findMembershipByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Member not found with id " + memberId));

        return membershipRepository.findByMember(member);
    }

    public void deleteMembership(Long id) {
        membershipRepository.deleteById(id);
    }

    public List<Membership> findAllMembership() {
        return membershipRepository.findAll();
    }

}