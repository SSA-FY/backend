package ssafy.lambda.notification.dto.item;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class VoteInfoItem {

    private Long voteInfoId;

    //한줄 평
    private String opinion;

    //공개 여부
    private Boolean isOpen;

    @QueryProjection
    public VoteInfoItem(Long voteInfoId, String opinion, Boolean isOpen) {
        this.voteInfoId = voteInfoId;
        this.opinion = opinion;
        this.isOpen = isOpen;
    }
}
