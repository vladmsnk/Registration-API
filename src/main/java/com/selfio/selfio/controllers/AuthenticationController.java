package com.selfio.selfio.controllers;

import com.selfio.selfio.dto.AuthenticationDto;
import com.selfio.selfio.entities.AuthenticatedUserInfo;
import com.selfio.selfio.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller for user authentication
 * Base endpoint: /api/v1/auth/
 */
@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationController {
    private final AuthService authService;
    /**
     * Autowired constructor
     * @param authService is the custom Service for user authentication.
     */
    @Autowired
    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }
    /**
     * Post request controller for "login" endpoint
     * @param request custom dto of class {@link AuthenticationDto}
     * @return {@link ResponseEntity<AuthenticatedUserInfo>}
     */
    @PostMapping("login")
    public ResponseEntity<AuthenticatedUserInfo> login(@RequestBody AuthenticationDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.authenticateUser(request));
    }

}
