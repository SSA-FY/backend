package ssafy.lambda.vote.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ResponseVoteDto {
    Long voteId;
    Long teamId;
    String content;
    String imgUrl;
    String teamName;
    Boolean voteWhether; //투표 여부

    @QueryProjection
    public ResponseVoteDto(Long voteId, Long teamId, String content, String imgUrl, String teamName, Boolean voteWhether) {
        this.voteId = voteId;
        this.teamId = teamId;
        this.teamName = teamName;
        this.content = content;
        this.imgUrl = imgUrl;
        this.voteWhether = voteWhether;
    }
}
