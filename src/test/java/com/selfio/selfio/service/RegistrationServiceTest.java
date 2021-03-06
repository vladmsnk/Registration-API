package com.selfio.selfio.service;

import com.selfio.selfio.email.EmailSender;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.exceptions.AlreadyExistsException;
import com.selfio.selfio.repository.UserRepository;
import com.selfio.selfio.requests.UserRegisterRq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private EmailSender emailSenderService;

    @Mock
    private UserService userService;

    private  RegistrationService registrationService;

    @BeforeEach
    void setRegistrationService() {
        registrationService = new RegistrationService(userService, userRepository, emailSenderService, jwtService);
        registrationService.setConfirmationLink("<h1> <a href='http://${server.host}:${server.port}/confirmation?token=%s'> Confirm Account</a> </h1>");
    }


    @Test
    void ShouldRegisterUser() {
        UserRegisterRq userRequest = new UserRegisterRq("vlad@mail.ru", "1324");


        doNothing().when(userService).saveUser(any(User.class));
        User user = new User(1, userRequest.getEmail(), userRequest.getPassword(), false);
        when(userService.createUserByRequest(userRequest)).thenReturn(user);

        when(jwtService.createToken(any(User.class))).thenReturn("token");
        registrationService.register(userRequest);
        verify(userService,times(1)).saveUser(any(User.class));
        verify(emailSenderService,times(1)).sendEmail(eq(userRequest.getEmail()), anyString());
    }

    @Test
    void shouldThrowExceptionWhenTryToRegisterAndUserExists() {
        UserRegisterRq userRegistrationDto = new UserRegisterRq("vlad@mail.ru", "1324");
        Mockito.doReturn(true)
                        .when(userRepository).existsByEmail(userRegistrationDto.getEmail());
        assertThatThrownBy(() -> registrationService.register(userRegistrationDto)).isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining("User with " + userRegistrationDto.getEmail() + " exists!");
    }

    @Test
    void shouldConfirmToken() {
        User user = new User(1, "kizaru@mail.ru", "1234", false);
        when(jwtService.createToken(user)).thenReturn("token");
        String token = jwtService.createToken(user);
        when(jwtService.isTokenExpired(anyString())).thenReturn(false);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        registrationService.confirmToken(token);
        assertThat(user.getVerified()).isTrue();
    }

}

