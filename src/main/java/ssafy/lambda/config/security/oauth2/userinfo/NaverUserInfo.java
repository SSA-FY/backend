package ssafy.lambda.config.security.oauth2.userinfo;

import java.util.Map;
import ssafy.lambda.member.entity.SocialType;

public class NaverUserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public SocialType getSocial() {
        return SocialType.Naver;
    }

    @Override
    public String getEmail() {
        return (String) ((Map) attributes.get("response")).get("email");
    }

    @Override
    public String getName() {
        return (String) ((Map) attributes.get("response")).get("nickname");
    }
}