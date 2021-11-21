package com.selfio.selfio.service;

import com.selfio.selfio.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.key}")
    private String secretKey;

    @Value("${jwt.timeDiff}")
    private int tokenLifeTime;

    public String generateToken(User user) {
        return Jwts.builder()
                .claim("userId", user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenLifeTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Integer extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return Integer.parseInt(claims.get("userId", String.class));
    }

    public Date extractExpirationTime(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractExpirationTime(token).before(new Date());
    }
}
