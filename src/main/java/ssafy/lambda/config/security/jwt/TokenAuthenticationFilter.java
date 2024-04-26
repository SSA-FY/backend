package ssafy.lambda.config.security.jwt;

import static ssafy.lambda.config.security.jwt.JwtProperties.HEADER_AUTHORIZATION;
import static ssafy.lambda.config.security.jwt.JwtProperties.TOKEN_PREFIX;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    // JWT 관련 서비스
    private final TokenService tokenService;

    // JWT 인증 필터 구현
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        String token = getAccessToken(authorizationHeader);

        if (tokenService.validToken(token)) {
            Authentication authentication = tokenService.getAuthentication(token);
            SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    // 헤더에서 JWT 토큰 추출
    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}

