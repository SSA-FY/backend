package ssafy.lambda.member.dto;

import java.util.UUID;
import lombok.Getter;
import ssafy.lambda.member.entity.Member;

@Getter
public class ResponseMemberDto {

    private final UUID memberId;
    private final String profileImgUrl;
    private final String email;
    private final String name;
    private final String tag;

    public ResponseMemberDto(Member member) {
        this.memberId = member.getMemberId();
        this.profileImgUrl = member.getProfileImgUrl();
        this.email = member.getEmail();
        this.name = member.getName();
        this.tag = member.getTag();
    }
}
