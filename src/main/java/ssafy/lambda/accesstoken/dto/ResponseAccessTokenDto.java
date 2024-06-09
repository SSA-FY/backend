package ssafy.lambda.accesstoken.dto;

import lombok.Builder;
import ssafy.lambda.member.entity.SocialType;

@Builder
public record ResponseAccessTokenDto(String token, Long memberId, String email, SocialType social,
                                     String name,
                                     String id, String profileImgUrl) {

}
