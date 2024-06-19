package ssafy.lambda.notification.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ResponseInvitionNotification{

    private static final String TITLE = "누군가 나를 초대했어요!";

    private Long invitationId;

    private String teamName;

    @QueryProjection
    public ResponseInvitionNotification(Long invitationId, String teamName) {
        this.invitationId = invitationId;
        this.teamName = teamName;
    }
}
