package ssafy.lambda.vote.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ResponseTodayVoteInfoDto {
    private Long voteInfoCnt;
    private Long opinionCnt;
    private Long totalPoint;

    @QueryProjection
    public ResponseTodayVoteInfoDto(Long voteInfoCnt, Long opinionCnt) {
        this.voteInfoCnt = voteInfoCnt;
        this.opinionCnt = opinionCnt;
        this.totalPoint = (voteInfoCnt * 100) + (opinionCnt * 50);
    }
}
