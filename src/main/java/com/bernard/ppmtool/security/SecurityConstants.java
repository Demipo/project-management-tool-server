package com.bernard.ppmtool.security;

import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/users";
    public static final String SECRET = "secretKeyForJsonWebToken";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final Long EXPIRATION_TIME = 3000000L;
}
