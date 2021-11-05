package com.selfio.selfio.service;

import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
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


    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    private UserService userService;

    @BeforeEach
    void setUserService() {
        userService = new UserService(userRepository, bCryptPasswordEncoder);
    }


    @Test
    public void shouldSaveUserWithEncodedPassword() {

        String pass = "12walenok24";
        User user = new User("walenok651", "walenok@mail.ru",pass, true);
        userService.saveUserWithEncodedPassword(user);
        verify(bCryptPasswordEncoder, times(1)).encode(pass);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());
    }

    @Test
    void shouldCreateUserByUserDto() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("vlad", "vlad@mail.ru", "12234");
        User created = userService.createUserByUseDTO(userRegistrationDto);
        User expected = new User("vlad", "vlad@mail.ru", "12234", false);
        assertThat(expected).isEqualTo(created);
    }
}