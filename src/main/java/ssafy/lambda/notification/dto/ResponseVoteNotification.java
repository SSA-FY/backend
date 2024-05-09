package ssafy.lambda.notification.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import ssafy.lambda.notification.dto.item.VoteInfoItem;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseVoteNotification extends ResponseNotification{

    private Long voteId;

    //내가 뽑힌 투표 정보
    private List<VoteInfoItem> voteInfoItems;

    @QueryProjection
    public ResponseVoteNotification(Long voteId, String content) {
        super("누군가 나를 뽑았어요!", content);
        this.voteId = voteId;
        this.voteInfoItems = new ArrayList<>();
    }
    public void addVoteInfoItems(VoteInfoItem voteInfoItem){
        this.voteInfoItems.add(voteInfoItem);
    }
}
