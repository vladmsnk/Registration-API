package com.selfio.selfio.service;

import com.google.gson.JsonSyntaxException;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.security.jwt.JwtAuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private long expiry;

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    public String createToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("email", user.getEmail());
        claims.put("userId", user.getId().toString());
        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + expiry);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractUserEmail(String token){
        return this.extractAllClaims(token).get("email").toString();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else return null;
    }

    public boolean validateToken(String token){
        try {
            return !this.isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException | JsonSyntaxException e) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            throw new JwtAuthenticationException("token is expired or invalid");
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build()
                .parseClaimsJws(token).getBody();
    }

    public Date extractExpiry(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiry(token).before(new Date());
    }

    public Integer extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return Integer.parseInt(claims.get("userId", String.class));
    }
}
