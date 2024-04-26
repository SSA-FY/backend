package ssafy.lambda.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ssafy.lambda.config.security.jwt.TokenAuthenticationFilter;
import ssafy.lambda.config.security.jwt.TokenProvider;
import ssafy.lambda.config.security.oauth2.OAuth2AuthorizationRequestBasedOnCookieRepository;
import ssafy.lambda.config.security.oauth2.OAuth2MemberCustomService;
import ssafy.lambda.config.security.oauth2.OAuth2SuccessHandler;
import ssafy.lambda.member.service.MemberService;

@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // cors off (개발용 설정)
        http.cors(AbstractHttpConfigurer::disable);

        // 불필요한 filter off
        http.csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable);

        // API 접속 허용 로직
        http.authorizeHttpRequests(
            auth -> auth.requestMatchers(new AntPathRequestMatcher("/**"))
                .authenticated()
                .anyRequest()
                .permitAll());

        return http.build();
    }
}
