package ssafy.lambda.membership.dto;

import lombok.Getter;
import ssafy.lambda.membership.entity.Membership;

@Getter
public class ResponseMembershipDto {


    private final String memberName;
    private final String memberTag;
    private final String nickname;
    private final String profileImgUrl;

    public ResponseMembershipDto(Membership membership) {

        this.memberName = membership.getMember()
                                    .getName();
        this.memberTag = membership.getMember()
                                   .getTag();
        this.nickname = membership.getNickname();
        this.profileImgUrl = membership.getMember().getProfileImgUrl();
    }
}
