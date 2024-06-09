package ssafy.lambda.global.security.oauth2.userinfo;

import java.util.Map;
import ssafy.lambda.member.entity.SocialType;

public class GoogleUserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public SocialType getSocial() {
        return SocialType.Google;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }


}
