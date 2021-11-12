package com.selfio.selfio.security;

import com.selfio.selfio.entities.User;
import com.selfio.selfio.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Spy
    private JwtService jwtService;


    @Test
    void shouldExtractId() {
        User user = new User(1, "vyumoiseenkov@miem.hse.ru", "12345", false);
        String tkn = jwtService.generateToken(user);
        Integer extractedId = jwtService.extractId(tkn);
        assertThat(extractedId).isEqualTo(user.getId());
    }

    @Test
    void testExtractExpirationTime() {
        User user = new User(1, "vyumoiseenkov@miem.hse.ru", "12345", false);
        String token = jwtService.generateToken(user);
        assertThat(jwtService.extractExpirationTime(token).getTime() > new Date().getTime()).isTrue();
    }

    @Test
    void testIsTokenExpired() {
        User user = new User(1, "vyumoiseenkov@miem.hse.ru", "12345", false);
        String token = jwtService.generateToken(user);

        assertThat(jwtService.isTokenExpired(token)).isFalse();
        verify(jwtService).extractExpirationTime(token);
    }


}