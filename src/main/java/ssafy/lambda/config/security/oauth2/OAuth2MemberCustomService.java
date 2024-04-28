package ssafy.lambda.config.security.oauth;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.entity.SocialType;
import ssafy.lambda.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class OAuth2MemberCustomService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User member = super.loadUser(userRequest);

        SocialType social = switch (userRequest.getClientRegistration()
            .getRegistrationId()) {
            case "google" -> SocialType.Google;
            default -> throw new OAuth2AuthenticationException("Provider Not Found");
        };

        saveOrUpdate(member, social);

        return member;
    }

    private Member saveOrUpdate(OAuth2User oAuth2User, SocialType social) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        Member member = memberRepository.findByEmailAndSocial(email, social)
            .orElse(Member.builder()
                .email(email)
                .social(social)
                .build());
        member.setName(name);

        return memberRepository.save(member);
    }
}

