package ssafy.lambda.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ssafy.lambda.config.security.jwt.TokenAuthenticationFilter;
import ssafy.lambda.config.security.jwt.TokenService;

@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    // JWT 관련
    private final TokenService tokenService;

    // Spring Security 필터 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // cors off (개발용 설정)
        http.cors(AbstractHttpConfigurer::disable);

        // 불필요한 filter off
        http.csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable);

        // JWT 인증 필터 추가
        http.addFilterBefore(tokenAuthenticationFilter(),
            RequestCacheAwareFilter.class);

        // API 접속 허용 로직
        http.authorizeHttpRequests(
            auth -> auth.requestMatchers(new AntPathRequestMatcher("/**"))
                .authenticated()
                .anyRequest()
                .permitAll());

        return http.build();
    }

    // JWT 인증 필터 Bean
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(
            tokenService);
    }
}