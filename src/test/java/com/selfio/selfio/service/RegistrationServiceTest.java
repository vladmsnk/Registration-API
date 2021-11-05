package com.selfio.selfio.service;

import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.email.EmailSenderService;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.UserRepository;
import com.selfio.selfio.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private UserService userService;

    private  RegistrationService registrationService;

    @BeforeEach
    void setRegistrationService() {
        registrationService = new RegistrationService(userService, userRepository, emailSenderService, jwtUtil);
    }


    @Test
    void ShouldRegisterUser() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("vlad", "vlad@mail.ru", "1324");
        registrationService.register(userRegistrationDto);
        verify(userService, times(1)).createUserByUseDTO(userRegistrationDto);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService,times(1)).saveUserWithEncodedPassword(userArgumentCaptor.capture());

        verify(jwtUtil,times(1)).generateToken(userArgumentCaptor.capture());

        verify(emailSenderService,times(1)).sendEmail(eq(userRegistrationDto.getEmail()), anyString());
    }

    @Test
    void shouldThrowExceptionWhenTryToRegisterAndUserExists() {

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("vlad", "vlad@mail.ru", "1324");
        Mockito.doReturn(true)
                        .when(userRepository).existsByEmail(userRegistrationDto.getEmail());
        assertThatThrownBy(() -> registrationService.register(userRegistrationDto)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User with " + userRegistrationDto.getEmail() + "exits!");
    }

//    @Test
//    void shouldConfirmToken() {
//        User user = new User("kizaru", "kizaru@mail.ru", "1234", false);
//        String token = jwtUtil.generateToken(user);
//
//        doReturn(true).when(userRepository).existsByLogin(user.getLogin());
//        registrationService.confirmToken(token);
//
//
////        verify(userRepository).findByLogin(jwtUtil.extractLogin(token));
////        boolean confirmed = registrationService.confirmToken(token);
////        doReturn(false).when(jwtUtil).isTokenExpired(token);
//////        verify(jwtUtil).isTokenExpired(token);
////        assertThat(confirmed).isTrue();
//
//
//    }

}

