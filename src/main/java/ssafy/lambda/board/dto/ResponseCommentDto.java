package ssafy.lambda.board.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseCommentDto {

    Long commentId;
    String writer;
    String content;
    LocalDateTime time;


}
