package ssafy.lambda.config.security.oauth2;

import static ssafy.lambda.config.security.jwt.JwtProperties.REFRESH_TOKEN_COOKIE_NAME;
import static ssafy.lambda.config.security.jwt.JwtProperties.REFRESH_TOKEN_DURATION;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ssafy.lambda.commons.utils.CookieUtil;
import ssafy.lambda.config.security.jwt.TokenService;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.entity.SocialType;
import ssafy.lambda.member.service.MemberService;

/**
 * oauth2 성공시 Handler
 */
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final String REDIRECT_PATH = "/token";

    private final TokenService tokenService;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final MemberService memberService;

    /**
     * oauth2 성공시 로직
     *
     * @param request        HttpServletRequest
     * @param response       HttpServletResponse
     * @param authentication 인증 정보
     */
    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Member member = memberService.findMemberByEmailAndSocial((String) oAuth2User.getAttributes()
            .get("email"), SocialType.Google);

        String refreshToken = tokenService.generateToken(member, REFRESH_TOKEN_DURATION);
        member.setRefreshToken(refreshToken);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken,
            (int) REFRESH_TOKEN_DURATION.toSeconds());

        clearAuthenticationAttributes(request, response);

        getRedirectStrategy().sendRedirect(request, response, REDIRECT_PATH);
    }

    /**
     * oauth2 처리 과정에서 저장했던 요청 정보 제거
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    private void clearAuthenticationAttributes(HttpServletRequest request,
        HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
