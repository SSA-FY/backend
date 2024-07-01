package ssafy.lambda.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsUtils;
import ssafy.lambda.global.security.jwt.TokenAuthenticationFilter;
import ssafy.lambda.global.security.jwt.TokenService;
import ssafy.lambda.global.security.oauth2.OAuth2AuthorizationRequestBasedOnCookieRepository;
import ssafy.lambda.global.security.oauth2.OAuth2MemberCustomService;
import ssafy.lambda.global.security.oauth2.OAuth2SuccessHandler;
import ssafy.lambda.member.service.MemberService;

//@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    // JWT 관련 서비스
    private final TokenService tokenService;

    // Oauth2 관련 서비스
    private final OAuth2MemberCustomService oAuth2MemberCustomService;
    private final MemberService memberService;

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

        // oauth2 추가
        http.oauth2Login(
            (oauth2) -> {
                oauth2
                    .authorizationEndpoint(endpoint -> endpoint
                        .authorizationRequestRepository(
                            oAuth2AuthorizationRequestBasedOnCookieRepository())
                    )
                    .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                        .userService(oAuth2MemberCustomService))
                    .successHandler(oAuth2SuccessHandler());
            }
        );

        // API 접속 허용 로직
        http.authorizeHttpRequests(
            auth -> auth
                .requestMatchers(
                    // 개발용
                    new AntPathRequestMatcher("/token/member"),
                    new AntPathRequestMatcher("/token/refresh"),
                    new AntPathRequestMatcher("/token"),
                    new AntPathRequestMatcher("/api"),
                    new AntPathRequestMatcher("/swagger-ui/**"),
                    new AntPathRequestMatcher("/api-docs/**"),
                    CorsUtils::isPreFlightRequest
                )
                .permitAll()
                .anyRequest()
                .authenticated());

        return http.build();
    }

    // JWT 인증 필터 Bean
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(
            tokenService);
    }

    // Oauth2 성공 Handler
    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenService,
            oAuth2AuthorizationRequestBasedOnCookieRepository(),
            memberService
        );
    }

    // Oauth2 요청 정보 저장 Repository
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }
}