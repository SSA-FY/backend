package ssafy.lambda.global.config;

import io.minio.MinioClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties("minio")
public class MinioConfig {

    private String url;
    private String id;
    private String password;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                          .endpoint(url)
                          .credentials(id, password)
                          .build();
    }
}