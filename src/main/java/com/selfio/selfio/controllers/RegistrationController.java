package com.selfio.selfio.controllers;

import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "registration")
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public UserRegistrationDto registration() {
        return new UserRegistrationDto("test@mail.ru", "1234");
    }

    @PostMapping("/registration")
    public String registration(@RequestBody UserRegistrationDto userRegistrationDto) {
//            userService.registerNewUserAccount(userRegistrationDto);
        return "hello";
    }


}