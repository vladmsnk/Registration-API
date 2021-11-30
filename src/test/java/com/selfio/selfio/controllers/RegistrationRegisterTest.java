package com.selfio.selfio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selfio.selfio.exceptions.AlreadyExistsException;
import com.selfio.selfio.exceptions.ExpiredTokenException;
import com.selfio.selfio.exceptions.UserNotFoundException;
import com.selfio.selfio.requests.UserRegisterRq;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.UserRepository;
import com.selfio.selfio.service.JwtService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegistrationRegisterTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private UserRepository userRepository;

    @SpyBean
    private JwtService jwtService;

    @Test
    @Order(value = 7)
    void ShouldSuccessfullyRegister() throws Exception {
        UserRegisterRq userRequest = new UserRegisterRq(
                "kamedvedev@miem.hse.ru",
                "1234567"
        );
        this.mockMvc.perform(post("/registration")
                        .content(asJsonString(userRequest)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @Order(1)
    void badLoginFieldTest() throws Exception {
        UserRegisterRq userRequest = new UserRegisterRq(
                "",
                "vyumoiseenkov@miem.hse.ru"
        );
        this.mockMvc.perform(post("/registration").content(asJsonString(userRequest)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Order(2)
    void badEmailFieldTest() throws Exception {
        UserRegisterRq userRequest = new UserRegisterRq(
                "vyumoiseenkovmiem.hse.ru",
                "1234567"
        );

        this.mockMvc.perform(post("/registration").content(asJsonString(userRequest)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Order(3)
    void inCorrectPasswordTest() throws Exception {
        UserRegisterRq userRequest = new UserRegisterRq(
                "vyumoiseenkovmiem.hse.ru",
                "1"
        );
        this.mockMvc.perform(post("/registration").content(asJsonString(userRequest)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    @Order(4)
    void shouldNotSendAnEmailWhenUserDtoIsCorrectButEmailDoesNotExists() throws Exception {
        UserRegisterRq userRequest = new UserRegisterRq(
                "vlad@test.ru",
                "123455324");
        this.mockMvc.perform(post("/registration").content(asJsonString(userRequest)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Order(5)
    public void shouldNotRegisterWhenUserAlreadyExists() throws Exception {
        UserRegisterRq userRequest = new UserRegisterRq(
                "vyumoiseenkov@miem.hse.ru",
                "1234567"
        );
        given(userRepository.existsByEmail(userRequest.getEmail())).willReturn(true);
        this.mockMvc.perform(post("/registration")
                        .content(asJsonString(userRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AlreadyExistsException))
                .andExpect(result -> assertEquals(result.getResolvedException().getMessage(), "User with " + userRequest.getEmail() + " exists!"));
    }

    @Test
    @Order(6)
    public void shouldConfirmAccount() throws Exception {
        User user = new User(1, "moiseenkov-v@mail.ru", "323232", false);
        String token = jwtService.createToken(user);
        given(userRepository.existsById(anyInt())).willReturn(true);
        given(userRepository.findById(anyInt())).willReturn(Optional.of(user));
        this.mockMvc.perform(get("/confirmation?token=" + token)).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(0)
    public void shouldNotConfirmAccountWhenUserDoesntExist() throws Exception {
        User user1 = new User(1, "moiseenkov-v@mail.ru", "323232", false);
        String token = jwtService.createToken(user1);
        given(userRepository.existsById(anyInt())).willReturn(false);
        this.mockMvc.perform(get("/confirmation?token=" + token)).andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                .andExpect(result -> assertEquals(result.getResolvedException().getMessage(), "User with id = " + user1.getId() + " was not found!"));


    }

    @Test
    @Order(8)
    public void shouldNotConfirmAccountWhenTokenIsExpired() throws Exception {
        User user = new User(1, "moiseenkov-v@mail.ru", "323232", false);
        String expiredToken = jwtService.createToken(user);
        given(userRepository.existsById(anyInt())).willReturn(true);
        given(jwtService.isTokenExpired(expiredToken)).willReturn(true);
        this.mockMvc.perform(get("/confirmation?token=" + expiredToken)).andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ExpiredTokenException))
                .andExpect(result -> assertEquals(result.getResolvedException().getMessage(), "Token is expired!"));
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
