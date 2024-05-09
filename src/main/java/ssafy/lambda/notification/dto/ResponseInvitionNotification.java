package ssafy.lambda.notification.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ResponseInvitionNotification extends ResponseNotification{

    private Long invitationId;

    @QueryProjection
    public ResponseInvitionNotification(Long invitationId, String content) {
        super("그룹에 초대되었어요!", content);
        this.invitationId = invitationId;
    }
}
