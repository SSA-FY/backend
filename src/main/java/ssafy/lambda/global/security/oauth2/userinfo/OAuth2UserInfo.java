package ssafy.lambda.global.security.oauth2.userinfo;

import ssafy.lambda.member.entity.SocialType;

public interface OAuth2UserInfo {

    SocialType getSocial();

    String getEmail();

    String getName();
}
