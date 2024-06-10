package ssafy.lambda.member.dto;

import java.util.List;
import java.util.UUID;
import lombok.Getter;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.membership.entity.Membership;

@Getter
public class ResponseMemberDto {

    private final UUID memberId;
    private final String profileImgUrl;
    private final String email;

    public ResponseMemberDto(Member member) {
        this.memberId = member.getMemberId();
        this.profileImgUrl = member.getProfileImgUrl();
        this.email = member.getEmail();
    }
}
