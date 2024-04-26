package ssafy.lambda.config.security.jwt;

import java.time.Duration;
import javax.crypto.SecretKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {
    
    private String issuer;
    private SecretKey secretKey;
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofMinutes(15);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofHours(12);
}
