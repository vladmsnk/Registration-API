package com.selfio.selfio.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private long validityTime;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @PostConstruct
//    protected void init() {
//        secret = Base64.getEncoder().encodeToString(secret.getBytes());
//    }

    public String createToken(Integer id, List<String> roles) {
        Claims claims = Jwts.claims();
        claims.put("id", id);
        //claims.put("roles", roles);
        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + validityTime);
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getEmail(token));
        return null;
    }
    public String getEmail(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJwt(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorisation");
        if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            return bearerToken.substring(7, bearerToken.length());
        } else return null;
    }

    public boolean validateToken(String token){
        try {
            Jws<Claims> authTokenInformation = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !authTokenInformation.getBody().getExpiration().before(new Date(System.currentTimeMillis()));
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("token is expired or invalid");
        }
    }
}
