package ssafy.lambda.invitation.dto;

import lombok.Data;

@Data
public class RequestAcceptInvitationDto {

    private Long invitationId;
    private String nickname;
}
