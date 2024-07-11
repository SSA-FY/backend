package ssafy.lambda.invitation.service;

import java.util.List;
import java.util.UUID;
import ssafy.lambda.invitation.dto.RequestAcceptInvitationDto;
import ssafy.lambda.invitation.entity.Invitation;

public interface InvitationService {

    public void createInvitation(UUID memberId, Long teamId);

    public void acceptInvitation(RequestAcceptInvitationDto requestAcceptInvitation, UUID memberId);

    public void rejectInvitation(Long invitationId);

    public void deleteInvitation(Long invitationId);

    public List<Invitation> getAllInvitations();
}
