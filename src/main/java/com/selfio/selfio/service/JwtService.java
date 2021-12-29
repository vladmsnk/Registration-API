package com.selfio.selfio.service;

import com.google.gson.JsonSyntaxException;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.security.jwt.JwtAuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
 * Jwt parsing Service.
 */
@Service
public class JwtService {

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private long expiry;

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    /**
     * Provides token creating for authenticated user.
     * @param user is object of entity 'users'.
     * @return JWT token.
     */
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

    /**
     *
     * @param token is the received JWT token.
     * @return email of the user.
     */
    public String extractUserEmail(String token){
        return this.extractAllClaims(token).get("email").toString();
    }

    /**
     *
     * @param request is object of {@link HttpServletRequest}.
     * @return token from the header of received request.
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else return null;
    }

    /**
     *
     * @param token is the received JWT token.
     * @return true if the token is valid and not expired.
     * @throws JwtAuthenticationException is a custom exception.
     */
    public boolean validateToken(String token){
        try {
            return !this.isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException | JsonSyntaxException e) {
            throw new JwtAuthenticationException("token is expired or invalid");
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build()
                .parseClaimsJws(token).getBody();
    }

    /**
     * Allows receiving of token expiry.
     * @param token is the received JWT token.
     * @return object of {@link Date} if token is valid.
     */
    public Date extractExpiry(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    /**
     * Method for checking token expiration.
     * @param token is the received JWT token.
     * @return true if the token is valid and not expired.
     */
    public boolean isTokenExpired(String token) {
        return extractExpiry(token).before(new Date());
    }

    /**
     *
     * @param token is the received JWT token.
     * @return id of the user.
     */
    public Integer extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return Integer.parseInt(claims.get("userId", String.class));
    }
}
