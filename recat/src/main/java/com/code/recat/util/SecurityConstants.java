package com.code.recat.util;


import org.springframework.beans.factory.annotation.Value;

public class SecurityConstants {
    public static final int EXPIRATION_DATE = 602_000_000; // 5 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String JWT_ISSUER = "recat";
    public static final String AUTHORITIES = "authorities";
    public static final String AUDIENCE = "recat-application";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED = "You do not have permission to access this page";
    public static final String[] PUBLIC_URLS = {"api/v1/auth/**", "/h2-console/**"};
    public static String SECRET_KEY = System.getenv("jwt_secret");
}
