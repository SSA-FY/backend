package ssafy.lambda.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.entity.SocialType;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RequestMemberDto {

    private SocialType social;
    private String name;
    private Integer point;
    private String profileImgUrl;
    private String email;

    public Member toEntity() {
        return Member.builder()
            .social(social)
            .name(name)
            .point(point)
            .profileImgUrl(profileImgUrl)
            .email(email)
            .build();
    }
}
