package ssafy.lambda.global.security.oauth2;

import static ssafy.lambda.global.config.JwtConfig.REFRESH_TOKEN_COOKIE_NAME;
import static ssafy.lambda.global.config.JwtConfig.REFRESH_TOKEN_DURATION;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;
import ssafy.lambda.global.security.jwt.TokenService;
import ssafy.lambda.global.utils.CookieUtil;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.service.MemberService;

/**
 * oauth2 성공시 Handler
 */
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${config.frontend}")
    private String frontend;

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

        Member oAuth2User = (Member) authentication.getPrincipal();

        Member member = memberService.findMemberByEmailAndSocial(oAuth2User.getEmail(),
            oAuth2User.getSocial());

        String refreshToken = tokenService.generateToken(member, REFRESH_TOKEN_DURATION);
        member.setRefreshToken(refreshToken);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken,
            (int) REFRESH_TOKEN_DURATION.toSeconds());

        clearAuthenticationAttributes(request, response);

        if (member.getMemberId() == null) {
            getRedirectStrategy().sendRedirect(request, response, frontend + "/signup");
        } else {
            getRedirectStrategy().sendRedirect(request, response, frontend + "/group");
        }
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
