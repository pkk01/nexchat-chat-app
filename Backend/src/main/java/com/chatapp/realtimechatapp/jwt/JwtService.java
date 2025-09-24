package com.chatapp.realtimechatapp.jwt;
import io.jsonwebtoken.Claims;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.sercret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    // method to extract the user id from the JWT Token

    public Long extractUserId(String jwtToken) {

        String userIdStr = extractClaim(jwtToken, claims ->
                claims.get("userId", String.class));

        return userIdStr != null ? Long.parseLong(userIdStr) : null;
    }

    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String jwtToken) {

        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    public SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
    
}
