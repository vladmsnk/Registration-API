package com.selfio.selfio.security;

import com.selfio.selfio.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @Spy
    private JwtUtil jwtUtil;


    @Test
    void testExtractLogin() {
        User user = new User("vladmsnk", "vyumoiseenkov@miem.hse.ru", "12345", false);
        doReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2bGFkbXNuayIsImV4cCI6MTYzNjExOTEyOSwiaWF0IjoxNjM2MTE1NTI5fQ.pVzkEOSNVnsbbj-5Aa2J4RI5aNYmgHnoJJS9nmavxkU")
                .when(jwtUtil).generateToken(user);
        String token = jwtUtil.generateToken(user);
        String extractedLogin = jwtUtil.extractLogin(token);
        assertThat(extractedLogin).isEqualTo(user.getLogin());
    }

    @Test
    void testExtractExpirationTime() {
        User user = new User("vladmsnk", "vyumoiseenkov@miem.hse.ru", "12345", false);
        String token = jwtUtil.generateToken(user);
        assertThat(jwtUtil.extractExpirationTime(token).getTime() > new Date().getTime()).isTrue();
    }

    @Test
    void testIsTokenExpired() {
        User user = new User("vladmsnk", "vyumoiseenkov@miem.hse.ru", "12345", false);
        String token = jwtUtil.generateToken(user);
        assertThat(jwtUtil.isTokenExpired(token)).isFalse();
        String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2bGFkbXNuayIsImV4cCI6MTYzNjExOTEyOSwiaWF0IjoxNjM2MTE1NTI5fQ.pVzkEOSNVnsbbj-5Aa2J4RI5aNYmgHnoJJS9nmavxkU";
        assertThat(jwtUtil.isTokenExpired(expiredToken)).isTrue();
    }


}