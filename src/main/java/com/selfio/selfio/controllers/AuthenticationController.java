package com.selfio.selfio.controllers;

import com.selfio.selfio.dto.AuthenticationDto;
import com.selfio.selfio.entities.AuthenticatedUserInfo;
import com.selfio.selfio.entities.TempRoles;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.security.jwt.JwtTokenProvider;
import com.selfio.selfio.services.AuthService;
import com.selfio.selfio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }
    @PostMapping("login")
    public ResponseEntity<AuthenticatedUserInfo> login(@RequestBody AuthenticationDto request){
        return ResponseEntity.ok((new AuthService(authenticationManager, jwtTokenProvider, userService).authenticateUser(request)));
    }

}
