package com.example.bookapi.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityConstants {

    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

    public static final String[] WHITE_LIST_URL = {
            "/api/auth/**",
            /*"/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"*/
    };
}
