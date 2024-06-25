package ssafy.lambda.vote.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class ResponseVoteWithVoteInfoListDto {
    //Vote 정보와 VoteInfoList 정보를 함께 반환하는 Dto
    private Long voteId;
    private String content;
    private List<ResponseVoteInfoToMeDto> responseVoteInfoToMeDtoList;

    @Builder
    public ResponseVoteWithVoteInfoListDto(Long voteId, String content,
        List<ResponseVoteInfoToMeDto> responseVoteInfoToMeDtoList) {
        this.voteId = voteId;
        this.content = content;
        this.responseVoteInfoToMeDtoList = responseVoteInfoToMeDtoList;
    }
}
