package ssafy.lambda.accesstoken.dto;

import java.util.UUID;
import lombok.Builder;
import ssafy.lambda.member.entity.SocialType;

@Builder
public record ResponseAccessTokenDto(String token, UUID memberId, String email, SocialType social,
                                     String name,
                                     String tag, String profileImgUrl) {

}
