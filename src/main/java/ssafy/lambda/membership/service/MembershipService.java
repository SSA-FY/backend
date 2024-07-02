package ssafy.lambda.membership.service;

import java.util.List;
import java.util.UUID;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.membership.entity.Membership;
import ssafy.lambda.team.entity.Team;

public interface MembershipService {

    public Membership createMembership(Member member, Team team, String nickname);

    public Membership findMembershipByMemberIdAndTeamId(UUID memberId, Long teamId);

    public List<Membership> findMembershipByTeamId(Long teamId);

    public List<Membership> findMembershipByMemberId(UUID memberId);

    public List<Membership> findMembershipByTeam(Team team);

    public List<Membership> findMembershipByMember(Member member);

    public void deleteMembership(Long membershipId);

    public List<Membership> findAllMembership();

    public void updateNickname(Membership membership, String newNickname);

    public boolean duplicatedMembershipCheck(Member member, Team team);

    public boolean duplicatedNicknameCheck(Team team, String nickname);
}