package ssafy.lambda.vote.dto;

import lombok.ToString;

@ToString
public class VoteResult {

    private Long voteeId;
    private Long voteCount;
    private Double percent;


    public VoteResult(Long voteeId, Long voteCount, Double percent) {
        this.voteeId = voteeId;
        this.voteCount = voteCount;
        this.percent = percent;
    }
}
