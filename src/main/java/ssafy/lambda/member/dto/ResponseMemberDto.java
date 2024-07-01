package ssafy.lambda.member.dto;

import java.util.UUID;
import lombok.Getter;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.entity.SocialType;

@Getter
public class ResponseMemberDto {

    private final UUID memberId;
    private final String profileImgUrl;
    private final String email;
    private final SocialType social;
    private final String name;
    private final String tag;

    public ResponseMemberDto(Member member) {
        this.memberId = member.getMemberId();
        this.profileImgUrl = member.getProfileImgUrl();
        this.email = member.getEmail();
        this.social = member.getSocial();
        this.name = member.getName();
        this.tag = member.getTag();
    }
    
}
