package ssafy.lambda.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ssafy.lambda.global.config.JwtConfig;
import ssafy.lambda.member.entity.Member;

@RequiredArgsConstructor
@Service
public class TokenService {

    // JWT 설정
    private final JwtConfig jwtConfig;

    // JWT 생성
    public String generateToken(Member member, Duration expiredAt) {
        Date now = new Date();
        return generateTokenHelper(new Date(now.getTime() + expiredAt.toMillis()), member);
    }

    // JWT 생성 Helper
    private String generateTokenHelper(Date expiry, Member member) {
        Date now = new Date();

        return Jwts.builder()
                   .header()
                   .add("typ", "JWT")
                   .and()
                   .issuer(jwtConfig.getIssuer())
                   .issuedAt(now)
                   .expiration(expiry)
                   .subject(member.getMemberId()
                                  .toString())
                   .signWith(jwtConfig.getSecretKey())
                   .compact();
    }

    // JWT 유효성 검사
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // JWT 권한 설정
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(
            new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
            new User(claims.getSubject
                               (), "", authorities), token, authorities);
    }

    // JWT Claims 추출
    private Claims getClaims(String token) {
        return Jwts.parser()
                   .verifyWith(jwtConfig.getSecretKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }
}
