package ssafy.lambda.vote.dto;

import lombok.Data;

@Data
public class RequestReviewDto {
    Long voteId;
    String review;
}
