package dev.lxqtpr.linda.restapi.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "security.jwt")
@Component
public class JwtProperties {
    private String accessSecret;
    private String refreshSecret;
    private Long accessExpirationInMinutes;
    private Long refreshExpirationInMinutes;
}