package ssafy.lambda.membership.service;

import java.util.List;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.membership.entity.Membership;
import ssafy.lambda.team.entity.Team;

public interface MembershipService {

    public Membership createMembership(Member member, Team team, String nickname);

    public Membership findMembershipByMemberIdAndTeamId(Long memberId, Long teamId);

    public List<Membership> findMembershipByTeamId(Long teamId);

    public List<Membership> findMembershipByMemberId(Long memberId);

    public List<Membership> findMembershipByTeam(Team team);

    public List<Membership> findMembershipByMember(Member member);

    public void deleteMembership(Long id);

    public List<Membership> findAllMembership();

    public void updateNickname(Member member, Team team, String newNickname);

    public boolean duplicatedMembershipCheck(Member member, Team team);

    public boolean duplicatedNicknameCheck(Team team, String nickname);
}