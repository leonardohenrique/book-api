package com.example.bookapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private SecurityProperties security = new SecurityProperties();
    private CorsProperties cors = new CorsProperties();

    @Data
    public static class SecurityProperties {
        private JwtProperties jwt = new JwtProperties();
    }

    @Data
    public static class JwtProperties {
        private String secretKey;
        private long expiration;
        private long refreshExpiration;
    }

    @Data
    public static class CorsProperties {
        private Boolean allowCredentials = true;
        private List<String> allowedOrigins = List.of("http://localhost:4200", "http://127.0.0.1:4200");
        private List<String> allowedMethods = List.of("*");
        private List<String> allowedHeaders = List.of("*");
    }
}
