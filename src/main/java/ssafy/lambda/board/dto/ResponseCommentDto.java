package ssafy.lambda.board.dto;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class ResponseCommentDto {

    Long commentId;
    String commenterName;
    String content;
    String time; // 최초 작성시간

    public ResponseCommentDto(Long commentId, String commenterName, String content,
        Instant createdAt) {
        this.commentId = commentId;
        this.commenterName = commenterName;
        this.content = content;
        this.time = createdAt.atZone(ZoneId.of("Asia/Seoul"))
                             .format(
                                 DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm"));
    }
}
