package com.selfio.selfio.service;

import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;


    private UserService userService;



    @BeforeEach
    void setUserService() {
        userService = new UserService(userRepository, new BCryptPasswordEncoder());
    }


    @Test
    public void shouldSaveUserWithEncodedPassword() {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User("walenok651", "walenok@mail.ru", bCryptPasswordEncoder.encode("12walenok24"), true);

        userService.saveUserWithEncodedPassword(user);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        User expected = userArgumentCaptor.getValue();

        assertThat(expected).isEqualTo(user);
    }
}