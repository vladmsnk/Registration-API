package com.selfio.selfio.controllers;

import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping(path = "/confirmation")
    public String confirm(@RequestParam("token")  String token) {
        return registrationService.confirmToken(token);
    }


    @PostMapping(path = "/registration")
    public String register(@Valid @RequestBody UserRegistrationDto userRegistrationDto){
        return registrationService.register(userRegistrationDto);
    }
}