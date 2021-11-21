package com.selfio.selfio.security;

import com.selfio.selfio.entities.User;
import com.selfio.selfio.service.JwtService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Spy
    private JwtService jwtService;

    @Test
    void shouldExtractId() {
        User user = new User(1, "vyumoiseenkov@miem.hse.ru", "12345", false);

        ReflectionTestUtils.setField(jwtService, "secretKey", "secret");
        ReflectionTestUtils.setField(jwtService, "tokenLifeTime", 3600000);
        String tkn = jwtService.generateToken(user);

        Integer extractedId = jwtService.extractUserId(tkn);
        assertEquals(user.getId(), extractedId);
    }

    @Test
    void testExtractExpirationTime() {
        User user = new User(1, "vyumoiseenkov@miem.hse.ru", "12345", false);
        ReflectionTestUtils.setField(jwtService, "secretKey", "secret");
        ReflectionTestUtils.setField(jwtService, "tokenLifeTime", 3600000);
        String token = jwtService.generateToken(user);
        assertTrue(jwtService.extractExpirationTime(token).getTime() > new Date().getTime());
    }

    @Test
    void testIsTokenExpired() {
        User user = new User(1, "vyumoiseenkov@miem.hse.ru", "12345", false);
        ReflectionTestUtils.setField(jwtService, "secretKey", "secret");
        ReflectionTestUtils.setField(jwtService, "tokenLifeTime", 3600000);
        String token = jwtService.generateToken(user);
        
        assertFalse(jwtService.isTokenExpired(token));
        verify(jwtService).extractExpirationTime(token);
    }


}