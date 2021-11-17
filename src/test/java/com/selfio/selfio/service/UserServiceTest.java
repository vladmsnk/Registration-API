package com.selfio.selfio.service;

import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.UserRepository;

import com.selfio.selfio.requests.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserService userService;

    @BeforeEach
    void setUserService() {
        userService = new UserService(userRepository, bCryptPasswordEncoder);
    }


    @Test
    public void shouldSaveUser() {

        String pass = "12walenok24";
        User user = new User("walenok@mail.ru", pass, true);
        userService.saveUser(user);
        verify(bCryptPasswordEncoder, times(1)).encode(pass);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());
    }

}