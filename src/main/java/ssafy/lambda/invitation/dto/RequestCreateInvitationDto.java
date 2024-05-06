package ssafy.lambda.invitation.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RequestCreateInvitationDto {

    private Long memberId;
    private Long teamId;
}
