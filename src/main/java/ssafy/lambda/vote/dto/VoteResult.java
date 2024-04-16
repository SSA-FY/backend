package ssafy.lambda.vote.dto;

import lombok.Data;
import lombok.ToString;

@ToString
public class VoteResult {

    private Long choosedMemberId;
    private Long voteCount;
    private Double percent;


    public VoteResult(Long choosedMemberId, Long voteCount, Double percent) {
        this.choosedMemberId = choosedMemberId;
        this.voteCount = voteCount;
        this.percent = percent;
    }
}
