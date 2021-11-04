package com.selfio.selfio.security;

import com.selfio.selfio.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @Test
    void ShouldGenerateToken() {
        JwtUtil jwtUtil = new JwtUtil();
        User user = createUser("admiral", "admiral@mail.ru", "password", true);
        String gottenToken = jwtUtil.generateToken(user);

    }

    @Test
    void extractLogin() {
    }

    @Test
    void extractExpirationTime() {
    }

    @Test
    void isTokenExpired() {
    }

    private User createUser(String login, String email, String password, boolean isVerified) {
        return new User(
                login,
                email,
                password,
                isVerified
        );
    }
}