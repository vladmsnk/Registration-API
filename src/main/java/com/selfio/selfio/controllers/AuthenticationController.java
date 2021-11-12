package com.selfio.selfio.controllers;

import com.selfio.selfio.dto.AuthenticationDto;
import com.selfio.selfio.entities.TempRoles;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.security.jwt.JwtTokenProvider;
import com.selfio.selfio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity login(@RequestBody AuthenticationDto request){
        try {
            String email = request.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.getPassword()));
            User user = userService.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            String token = jwtTokenProvider.createToken(user.getId(), TempRoles.getRoles());
            Map<Object, Object> response = new HashMap<>();
            response.put("email", email);
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

}
