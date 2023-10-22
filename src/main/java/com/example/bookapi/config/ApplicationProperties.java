package com.example.bookapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private Security security = new Security();

    @Data
    public static class Security {
        private Jwt jwt = new Jwt();
    }

    @Data
    public static class Jwt {
        private String secretKey;
        private long expiration;
        private long refreshExpiration;
    }
}
