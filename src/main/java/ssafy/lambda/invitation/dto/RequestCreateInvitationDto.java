package ssafy.lambda.invitation.dto;

import java.util.UUID;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RequestCreateInvitationDto {

    private UUID memberId;
    private Long teamId;
}
