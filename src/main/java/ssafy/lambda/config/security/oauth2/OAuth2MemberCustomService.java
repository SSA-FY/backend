package ssafy.lambda.config.security.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ssafy.lambda.config.security.oauth2.userinfo.GoogleUserInfo;
import ssafy.lambda.config.security.oauth2.userinfo.KakaoUserInfo;
import ssafy.lambda.config.security.oauth2.userinfo.NaverUserInfo;
import ssafy.lambda.config.security.oauth2.userinfo.OAuth2UserInfo;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.repository.MemberRepository;

/**
 * oauth2를 통해 얻은 멤버 정보 처리 서비스
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2MemberCustomService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    /**
     * oauth2를 통해 얻은 멤버 정보 처리
     *
     * @param userRequest oauth2를 통해 얻은 멤버 정보
     * @return Member
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;

        switch (userRequest.getClientRegistration()
                           .getRegistrationId()) {
            case "google":
                oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
                break;
            case "kakao":
                oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
                break;
            case "naver":
                oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
                break;
            default:
                throw new OAuth2AuthenticationException("Provider Not Found");
        }

        Member member = memberRepository.findByEmailAndSocial(oAuth2UserInfo.getEmail(),
                                            oAuth2UserInfo.getSocial())
                                        .orElse(Member.builder()
                                                      .email(oAuth2UserInfo.getEmail())
                                                      .social(oAuth2UserInfo.getSocial())
                                                      .build());

        member.setName(oAuth2UserInfo.getName());

        memberRepository.save(member);

        return member;
    }

}

