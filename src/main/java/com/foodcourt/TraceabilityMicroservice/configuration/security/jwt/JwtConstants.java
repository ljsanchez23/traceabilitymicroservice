package com.foodcourt.TraceabilityMicroservice.configuration.security.jwt;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtConstants {

    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_BEARER = "Bearer ";
    public static final String JWT_SECRET_KEY = System.getenv("SECRET_KEY");
    public static final String JWT_AUTHORITIES = "authorities";
    public static final String UNCHECKED = "unchecked";
    public static final String CLIENT_ROLE = "CLIENT";
    public static final String ACCEPT_PURCHASE_URL = "/purchase/accept";
    public static final String ASSISTANT_ROLE = "ASSISTANT";
    public static final String ADD_SUPPLY_URL = "/update/{productId}";

    public static SecretKey getSignedKey(String secretKey){
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
