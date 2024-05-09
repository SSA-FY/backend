package ssafy.lambda.notification.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ResponseExpiredVoteNotification{

    private static final String TITLE = "투표가 종료되었어요!";

    private Long expiredVoteId;

    private String expiredVoteTitle;

    @QueryProjection
    public ResponseExpiredVoteNotification(Long expiredVoteId, String expiredVoteTitle) {
        this.expiredVoteId = expiredVoteId;
        this.expiredVoteTitle = expiredVoteTitle;
    }
}
