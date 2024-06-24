package ssafy.lambda.board.dto;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class ResponseCommentDto {

    Long commentId;
    String commenter;
    String content;
    String time; // 최초 작성시간

    public ResponseCommentDto(Long commentId, String commenterName, String commenterTag,
        String content,
        Instant createdAt) {
        this.commentId = commentId;
        this.commenter = commenterName + "@" + commenterTag;
        this.content = content;
        this.time = createdAt.atZone(ZoneId.of("Asia/Seoul"))
                             .format(
                                 DateTimeFormatter.ofPattern("MM-dd HH:mm"));
    }
}
