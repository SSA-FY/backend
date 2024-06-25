package ssafy.lambda.board.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseBoardDetailDto {

    Long boardId;
    String content;
    Long totalCnt;

    List<MemberWithPercenCntDto> memberList = new ArrayList<>();

    @Builder
    public ResponseBoardDetailDto(Long boardId, String content, Long totalCnt) {
        this.boardId = boardId;
        this.content = content;
        this.totalCnt = totalCnt;
    }
    
    public void addMember(MemberWithPercenCntDto m) {
        memberList.add(m);
    }

}
