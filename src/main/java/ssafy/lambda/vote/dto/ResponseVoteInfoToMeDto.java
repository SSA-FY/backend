package ssafy.lambda.vote.dto;

import lombok.Builder;
import lombok.Data;
import ssafy.lambda.vote.entity.VoteInfo;

@Data
public class ResponseVoteInfoToMeDto {
    private Long voteInfoId;
    private String opinion;
    private Boolean isOpen;

    //open상태일 때 VoterTag정보 추가
    private String voterTag;

    @Builder
    public ResponseVoteInfoToMeDto(Long voteInfoId, String opinion, Boolean isOpen, String voterTag) {
        this.voteInfoId = voteInfoId;
        this.opinion = opinion;
        this.isOpen = isOpen;
        this.voterTag = voterTag;
    }

    public static ResponseVoteInfoToMeDto VoteInfoToDto(VoteInfo voteInfo) {
        return ResponseVoteInfoToMeDto.builder()
                                      .voteInfoId(voteInfo.getId())
                                      .opinion(voteInfo.getOpinion())
                                      .isOpen(voteInfo.getIsOpen())
                                      //Open이 안된 상태면 해당 정보를 숨겨서 전달
                                      .voterTag(voteInfo.getIsOpen() ? voteInfo.getVoter()
                                                                               .getTag() : null)
                                      .build();
    }
}
