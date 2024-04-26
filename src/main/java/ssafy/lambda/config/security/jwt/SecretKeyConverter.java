package ssafy.lambda.config.security.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class SecretKeyConverter implements Converter<String, SecretKey> {

    // 설정 주입 시 문자열 비밀키를 SecretKey 객체로 변환
    @Override
    public SecretKey convert(String secretKey) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
