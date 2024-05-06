package ssafy.lambda.invitation.dto;

import java.time.Instant;
import lombok.Data;
import ssafy.lambda.invitation.entity.Invitation;

@Data
public class ResponseInvitationDto {

    private Long invitationId;

    private Long memberId;

    private Long teamId;

    private Instant createdAt;

    private Instant invalidateAt;

    public ResponseInvitationDto(Invitation invitation) {
        this.invitationId = invitation.getId();
        this.memberId = invitation.getMember()
                                  .getMemberId();
        this.teamId = invitation.getTeam()
                                .getTeamId();
        this.createdAt = invitation.getCreatedAt();
        this.invalidateAt = invitation.getInvalidateAt();
    }

}
