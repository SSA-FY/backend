package ssafy.lambda.notification.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ResponseExpiredVoteNotification extends ResponseNotification{
    private Long expiredVoteId;

    private String expiredVoteTitle;

    @QueryProjection
    public ResponseExpiredVoteNotification(Long expiredVoteId, String content) {
        super("투표가 종료되었어요!", content);
        this.expiredVoteId = expiredVoteId;
    }
}
