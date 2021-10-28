package com.selfio.selfio.controllers;

import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

//@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserRegistrationDto());
        return "registration";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserRegistrationDto userRegistrationDto) {
        userService.registerNewUserAccount(userRegistrationDto);
        return "registration";
    }


}