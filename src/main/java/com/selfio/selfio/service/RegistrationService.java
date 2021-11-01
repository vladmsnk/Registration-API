package com.selfio.selfio.service;

import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegistrationService {

    private final UserService userService;

    @Autowired
    public RegistrationService(UserService userService) {
        this.userService = userService;
    }

    public String register(UserRegistrationDto userRegistrationDto) {
         return userService.signUpUser(
                 new User(
                         userRegistrationDto.getLogin(),
                         userRegistrationDto.getEmail(),
                         userRegistrationDto.getPassword(),
                         true
                 )
         );
    }
}
