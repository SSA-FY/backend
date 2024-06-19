package ssafy.lambda.notification.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import ssafy.lambda.notification.dto.item.VoteInfoItem;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseVoteNotification{

    private static final String TITLE = "누군가 나를 뽑았어요!";

    private Long voteId;

    private String voteTitle;

    //내가 뽑힌 투표 정보
    private List<VoteInfoItem> voteInfoItems;


    @QueryProjection
    public ResponseVoteNotification(Long voteId, String voteTitle, List<VoteInfoItem> voteInfoItems) {
        this.voteId = voteId;
        this.voteTitle = voteTitle;
        this.voteInfoItems = voteInfoItems;
    }
}
