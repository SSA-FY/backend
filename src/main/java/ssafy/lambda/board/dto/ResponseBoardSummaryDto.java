package ssafy.lambda.board.dto;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseBoardSummaryDto implements Comparable<Long> {

    Long boardId;
    String time;
    String content;
    List<String> memberList = new ArrayList<>();

    @Builder
    public ResponseBoardSummaryDto(Long boardId, Instant createdAt, String content) {
        this.boardId = boardId;
        this.time = createdAt.atZone(ZoneId.of("Asia/Seoul"))
                             .format(
                                 DateTimeFormatter.ofPattern("YYYY.MM.dd"));
        this.content = content;
    }

    public void addMember(String profileUrl) {
        memberList.add(profileUrl);
    }

    @Override
    public int compareTo(Long b) {
        return boardId.compareTo(b);
    }
}
