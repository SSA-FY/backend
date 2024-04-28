package ssafy.lambda.accesstoken.service;

import static ssafy.lambda.config.security.jwt.JwtProperties.ACCESS_TOKEN_DURATION;
import static ssafy.lambda.config.security.jwt.JwtProperties.REFRESH_TOKEN_COOKIE_NAME;
import static ssafy.lambda.config.security.jwt.JwtProperties.REFRESH_TOKEN_DURATION;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.lambda.commons.utils.CookieUtil;
import ssafy.lambda.config.security.jwt.JwtProperties;
import ssafy.lambda.config.security.jwt.TokenProvider;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.service.MemberService;

@Service
@RequiredArgsConstructor
public class AccessTokenService {

    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;
    private final MemberService memberService;

    @Transactional
    public String createAccessToken(HttpServletResponse httpServletResponse, String token) {
        if (tokenProvider.validToken(token)) {
            Claims claims = getClaims(token);

            Member member = memberService.findMemberById(Long.parseLong(claims.getSubject()));

            if (token.equals(member.getRefreshToken())) {
                String refreshToken = tokenProvider.generateToken(member, REFRESH_TOKEN_DURATION);
                CookieUtil.addCookie(httpServletResponse, REFRESH_TOKEN_COOKIE_NAME, refreshToken,
                    (int) REFRESH_TOKEN_DURATION.toSeconds());
                member.setRefreshToken(refreshToken);

                return tokenProvider.generateToken(member, ACCESS_TOKEN_DURATION);
            }
        }

        return null;
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
            .verifyWith(jwtProperties.getSecretKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}