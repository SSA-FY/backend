package ssafy.lambda.accesstoken.service;

import static ssafy.lambda.global.security.jwt.JwtProperties.ACCESS_TOKEN_DURATION;
import static ssafy.lambda.global.security.jwt.JwtProperties.REFRESH_TOKEN_COOKIE_NAME;
import static ssafy.lambda.global.security.jwt.JwtProperties.REFRESH_TOKEN_DURATION;
import static ssafy.lambda.global.security.oauth2.OAuth2AuthorizationRequestBasedOnCookieRepository.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;
import ssafy.lambda.global.security.jwt.JwtProperties;
import ssafy.lambda.global.security.jwt.TokenService;
import ssafy.lambda.global.utils.CookieUtil;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.service.MemberService;

/**
 * Access Token 발급 Service
 */
@Service
@RequiredArgsConstructor
public class AccessTokenService {

    private final TokenService tokenService;
    private final JwtProperties jwtProperties;
    private final MemberService memberService;

    /**
     * Access Token 발급
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Access Token
     */
    @Transactional
    public String createAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = WebUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        String token = CookieUtil.deserialize(cookie, String.class);

        if (tokenService.validToken(token)) {
            Claims claims = getClaims(token);

            Member member = memberService.findMemberById(UUID.fromString(claims.getSubject()));

            if (token.equals(member.getRefreshToken())) {
                String refreshToken = tokenService.generateToken(member, REFRESH_TOKEN_DURATION);
                CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken,
                    (int) REFRESH_TOKEN_DURATION.toSeconds());
                member.setRefreshToken(refreshToken);

                return tokenService.generateToken(member, ACCESS_TOKEN_DURATION);
            }
        }

        return null;
    }

    /**
     * Token Claims 추출
     *
     * @param token token
     * @return Token Claims
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                   .verifyWith(jwtProperties.getSecretKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }
}