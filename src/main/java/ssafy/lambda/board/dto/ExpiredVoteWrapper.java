package ssafy.lambda.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import ssafy.lambda.board.entity.ExpiredVote;
import ssafy.lambda.board.entity.ExpiredVoteInfo;


@Getter
public class ExpiredVoteWrapper {
    private ExpiredVote expiredVote;
    private ExpiredVoteInfo expiredVoteInfo;

    @QueryProjection
    public ExpiredVoteWrapper(ExpiredVote expiredVote, ExpiredVoteInfo expiredVoteInfo) {
        this.expiredVote = expiredVote;
        this.expiredVoteInfo = expiredVoteInfo;
    }
}
