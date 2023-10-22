package com.example.bookapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration {

    private final ApplicationProperties properties;

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                var allowedOriginPatterns = properties.getCors().getAllowedOrigins().toArray(String[]::new);

                registry.addMapping("/**").allowedOriginPatterns(allowedOriginPatterns);
            }
        };
    }

    @Bean
    CorsConfiguration corsConfiguration() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(properties.getCors().getAllowedOrigins());
        configuration.setAllowedMethods(properties.getCors().getAllowedMethods());
        configuration.setAllowedHeaders(properties.getCors().getAllowedHeaders());
        return configuration;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration());
        return source;
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }
}
