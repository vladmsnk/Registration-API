package com.selfio.selfio.controllers;

import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "registration")
public class RegistrationController {


    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public String register(@RequestBody UserRegistrationDto userRegistrationDto) {
        return registrationService.register(userRegistrationDto);
    }

}