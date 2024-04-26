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

    @Override
    public SecretKey convert(String secretKey) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
