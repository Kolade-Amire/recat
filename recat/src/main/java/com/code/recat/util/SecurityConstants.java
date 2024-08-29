package com.code.recat.util;


public class SecurityConstants {
    public static final Long JWT_EXPIRATION_DATE = 602000000L; // 5 days
    public static final Long REFRESH_TOKEN_EXPIRATION_DATE = 86400000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String JWT_ISSUER = "recat";
    public static final String AUTHORITIES = "authorities";
    public static final String AUDIENCE = "recat-application";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED = "You do not have permission to access this page";
    public static final String AUTHENTICATED_MESSAGE = "User authenticated successfully";
    public static final String REGISTERED_MESSAGE = "User registered successfully";

    public static final String[] PUBLIC_URLS = {
            AppConstants.BASE_URL + "/auth/**", "/h2-console/**", "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };
    public static String SECRET_KEY = System.getenv("jwt_secret");
}
