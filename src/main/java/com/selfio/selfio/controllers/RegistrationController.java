package com.selfio.selfio.controllers;

import com.selfio.selfio.errors.AlreadyExistsException;
import com.selfio.selfio.errors.ExpiredTokenException;
import com.selfio.selfio.errors.UserNotFoundException;
import com.selfio.selfio.requests.UserRequest;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RegistrationController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());


    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping(path = "/confirmation")
    public ResponseEntity<User> confirm(@RequestParam("token")  String token) {
            User confirmed = registrationService.confirmToken(token);
            LOGGER.debug("Confirmation of the token: {}", token);
            return new ResponseEntity<>(confirmed, HttpStatus.OK);
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<User> register(@Valid @RequestBody UserRequest userRequest){
        User registered =  registrationService.register(userRequest);
        LOGGER.debug("Registering user account with information: {}", userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(registered);
    }
}