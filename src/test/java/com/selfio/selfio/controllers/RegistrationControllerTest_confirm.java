package com.selfio.selfio.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.UserRepository;
import com.selfio.selfio.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class RegistrationControllerTest_confirm {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private RegistrationController registrationController;


    @Test
    public void shouldConfirmAccount() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("login", "kamedvedev@miem.hse.ru", "323232");
        String token = registrationController.register(userRegistrationDto);
        mockMvc.perform(get("/confirmation?token=" + token)).andDo(print()).andExpect(status().is3xxRedirection());
    }


    @Test
    public void shouldNotRegisterNewUserWhenAlreadyExists() throws Exception {
        when(userRepository.existsByEmail("kamedvedev@miem.hse.ru")).thenReturn(true);
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("login", "kamedvedev@miem.hse.ru", "323232");

        assertThatThrownBy(() -> registrationController.register(userRegistrationDto)).isInstanceOf(RuntimeException.class).hasMessageContaining(
                "User with " + userRegistrationDto.getEmail() + "exits!"
        );
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
