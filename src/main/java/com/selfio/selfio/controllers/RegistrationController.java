package com.selfio.selfio.controllers;

import com.selfio.selfio.entities.UserInfo;
import com.selfio.selfio.requests.UserRegisterRq;
import com.selfio.selfio.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/register/")
public class RegistrationController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final RegistrationService registrationService;
    /**
     * Autowired constructor.
     * @param registrationService is the custom Service for user authentication.
     */
    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Controller for get request.
     * @param token is the authorization token.
     * @return http status 200 and object of class {@link UserInfo}.
     */
    @GetMapping(path = "confirmation")
    public ResponseEntity<UserInfo> confirm(@RequestParam("token")  String token) {
        UserInfo confirmed = registrationService.confirmToken(token);
        LOGGER.debug("Confirmation of the token: {}", token);
        return ResponseEntity.status(HttpStatus.OK).body(confirmed);
    }

    /**
     * Controller for post request.
     * @param userRequest is the object with user credentials.
     * @return http status 201 and object of class {@link UserInfo}.
     */
    @PostMapping(path = "registration")
    public ResponseEntity<UserInfo> register(@Valid @RequestBody UserRegisterRq userRequest){
        UserInfo registered =  registrationService.register(userRequest);
        LOGGER.debug("Registering user account with information: {}", userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }
}