package ssafy.lambda.vote.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ResponseVoteDto {
    Long voteId;
    String content;
    String imgUrl;
    Boolean voteWhether; //투표 여부

    @QueryProjection
    public ResponseVoteDto(Long voteId, String content, String imgUrl, Boolean voteWhether) {
        this.voteId = voteId;
        this.content = content;
        this.imgUrl = imgUrl;
        this.voteWhether = voteWhether;
    }
}
