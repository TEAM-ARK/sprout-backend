package com.ark.inflearnback.domain.member.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.security.Key;
import java.util.Date;

public class JwtManager {
    private final long accessTokenTime;

    private final String secretKey;

    public JwtManager(String secretKey, long accessTokenTime) {
        this.secretKey = secretKey;
        this.accessTokenTime = 1000L * accessTokenTime;
    }

    private Key getSigninKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 해석
    public Claims tokenValidation(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(getSigninKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new InvalidParameterException("Token Authentication Has Expired.");
        }
    }

    public String getEmail(String token) {
        return tokenValidation(token).get("email", String.class);
    }

    // access token 생성
    public String generateAccessToken(String email) {
        return doGenerateToken(email, accessTokenTime);
    }

    private String doGenerateToken(String email, Long expiredTime) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
            .signWith(getSigninKey(secretKey), SignatureAlgorithm.HS256)
            .compact();
    }

}
