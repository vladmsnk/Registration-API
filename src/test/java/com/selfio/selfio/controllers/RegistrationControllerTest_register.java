package com.selfio.selfio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestBody;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class RegistrationControllerTest_register {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Test
    void ShouldSuccessfullyRegister() throws Exception{
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "nindzya",
                "kamedvedev@miem.hse.ru",
                "1234567"
        );
        this.mockMvc.perform(post("/registration")
                .content(asJsonString(userRegistrationDto)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

    }
    @Test
    void badLoginFieldTest() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "",
                "vyumoiseenkov@miem.hse.ru",
                "1234567"
        );
        this.mockMvc.perform(post("/registration").content(asJsonString(userRegistrationDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void badEmailFieldTest() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "vlad",
                "vyumoiseenkovmiem.hse.ru",
                "1234567"
        );

        this.mockMvc.perform(post("/registration").content(asJsonString(userRegistrationDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void inCorrectPasswordTest() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "vlad",
                "vyumoiseenkovmiem.hse.ru",
                "1"
        );
        this.mockMvc.perform(post("/registration").content(asJsonString(userRegistrationDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    void shouldNotSendAnEmailWhenUserDtoIsCorrectButEmailDoesNotExists() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
            "vlad",
                "vlad@test.ru",
                "123455324");
        this.mockMvc.perform(post("/registration").content(asJsonString(userRegistrationDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldNotRegisterWhenUserAlreaadyExists() throws Exception {
        User user = new User("nindzya", "vyumoiseenkov@miem.hse.ru", "1234567", false);
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "nindzya",
                "vyumoiseenkov@miem.hse.ru",
                "1234567"
        );
        userRepository.save(user);
        this.mockMvc.perform(post("/registration").content(asJsonString(userRegistrationDto)));
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
