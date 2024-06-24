package ssafy.lambda.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberWithPercenCntDto {

    String member;
    String profileImgUrl;
    Long voteCnt;
    Long percent;


    @Builder
    public MemberWithPercenCntDto(String memberName, String memberTag, String profileImgUrl,
        Long voteCnt, Long totalCnt) {
        this.member = memberName + "@" + memberTag;
        this.profileImgUrl = profileImgUrl;
        this.voteCnt = voteCnt;
        this.percent = voteCnt * 100 / totalCnt;
    }
}
