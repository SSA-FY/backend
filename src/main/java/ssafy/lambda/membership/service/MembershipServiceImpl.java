package ssafy.lambda.membership.service;


import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.exception.MemberNotFoundException;
import ssafy.lambda.member.repository.MemberRepository;
import ssafy.lambda.membership.entity.Membership;
import ssafy.lambda.membership.exception.AlreadyExistingMemberException;
import ssafy.lambda.membership.exception.DuplicatedNicknameException;
import ssafy.lambda.membership.repository.MembershipRepository;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.team.repository.TeamRepository;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final MembershipRepository membershipRepository;

    public Membership createMembership(Member member, Team team, String nickname) {
        Membership membership = Membership.builder()
                                          .member(member)
                                          .team(team)
                                          .nickname(nickname)
                                          .build();
        if (duplicatedMembershipCheck(member, team)) {
            throw new AlreadyExistingMemberException();
        }
        if (duplicatedNicknameCheck(team, nickname)) {
            throw new DuplicatedNicknameException(nickname);
        }
        return membershipRepository.save(membership);
    }

    @Transactional
    public Membership findMembershipByMemberIdAndTeamId(UUID memberId, Long teamId) {
        return membershipRepository.findByMemberIdAndTeamId(memberId, teamId)
                                   .orElseThrow(() -> new IllegalArgumentException(
                                       "Membership not found with memberId " + memberId
                                           + " and teamId " + teamId));
    }

    public List<Membership> findMembershipByTeamId(Long teamId) {
        Team team = teamRepository.findById(teamId)
                                  .orElseThrow(() -> new IllegalArgumentException(
                                      "Team not found with id " + teamId));
        return membershipRepository.findByTeam(team);
    }

    public List<Membership> findMembershipByMemberId(UUID memberId) {
        Member member = memberRepository.findById(memberId)
                                        .orElseThrow(() -> new MemberNotFoundException(memberId));
        return membershipRepository.findByMember(member);
    }

    public void deleteMembership(Long membershipId) {
        membershipRepository.deleteById(membershipId);
    }

    public List<Membership> findAllMembership() {
        return membershipRepository.findAll();
    }

    @Transactional
    public void updateNickname(Membership membership, String newNickname) {
        if (duplicatedNicknameCheck(membership.getTeam(), newNickname)) {
            throw new DuplicatedNicknameException(newNickname);
        }
        membership.setNickname(newNickname);
    }

    @Override
    public List<Membership> findMembershipByTeam(Team team) {
        return membershipRepository.findByTeam(team);
    }

    @Override
    public List<Membership> findMembershipByMember(Member member) {
        return membershipRepository.findByMember(member);
    }

    @Override
    public boolean duplicatedNicknameCheck(Team team, String nickname) {
        return membershipRepository.existsByTeamAndNickname(team, nickname);
    }

    @Override
    public boolean duplicatedMembershipCheck(Member member, Team team) {
        return membershipRepository.existsByMemberAndTeam(member, team);
    }
}
