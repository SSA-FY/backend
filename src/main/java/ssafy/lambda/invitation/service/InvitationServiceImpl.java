package ssafy.lambda.invitation.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.lambda.invitation.dto.RequestAcceptInvitationDto;
import ssafy.lambda.invitation.entity.Invitation;
import ssafy.lambda.invitation.exception.DuplicatedInvitationException;
import ssafy.lambda.invitation.exception.InvitationNotFoundException;
import ssafy.lambda.invitation.repository.InvitationRepository;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.service.MemberService;
import ssafy.lambda.membership.exception.AlreadyExistingMemberException;
import ssafy.lambda.membership.service.MembershipService;
import ssafy.lambda.notification.service.NotificationService;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.team.service.TeamService;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final MemberService memberService;
    private final TeamService teamService;
    private final MembershipService membershipService;
    private final NotificationService notificationService;

    @Transactional
    @Override
    public void createInvitation(UUID memberId, Long teamId) {
        Member member = memberService.findMemberById(memberId);
        Team team = teamService.findTeamById(teamId);
        if (membershipService.duplicatedMembershipCheck(member, team)) {
            throw new AlreadyExistingMemberException();
        }
        if (invitationRepository.existsByMemberAndTeam(member, team)) {
            throw new DuplicatedInvitationException();
        }
        Invitation invitation = Invitation.builder()
                                          .team(teamService.findTeamById(teamId))
                                          .member(memberService.findMemberById(memberId))
                                          .build();
        invitationRepository.save(invitation);
        notificationService.createInvitationNotification(member, invitation);
    }

    @Transactional
    @Override
    public void acceptInvitation(RequestAcceptInvitationDto requestAcceptInvitation) {
        Invitation invitation = invitationRepository.findById(
                                                        requestAcceptInvitation.getInvitationId())
                                                    .orElseThrow(
                                                        () -> new InvitationNotFoundException());
        membershipService.createMembership(invitation.getMember(), invitation.getTeam(),
            requestAcceptInvitation.getNickname());
        invitationRepository.delete(invitation);
    }

    @Override
    public void rejectInvitation(Long invitationId) {
        invitationRepository.deleteById(invitationId);
    }

    @Override
    public void deleteInvitation(Long invitationId) {
        //TODO 7일이 지난 초대는 자동 삭제되어야 함
    }

    @Override
    public List<Invitation> getAllInvitations() {
        return invitationRepository.findAll();
    }
}
