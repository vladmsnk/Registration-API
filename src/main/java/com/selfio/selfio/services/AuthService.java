package com.selfio.selfio.services;

import com.selfio.selfio.dto.AuthenticationDto;
import com.selfio.selfio.entities.AuthenticatedUserInfo;
import com.selfio.selfio.entities.TempRoles;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    public AuthenticatedUserInfo authenticateUser(AuthenticationDto requestInfo) throws UsernameNotFoundException, BadCredentialsException {
        try {
            String email = requestInfo.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestInfo.getPassword()));
            User user = userService.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            String token = jwtTokenProvider.createToken(user.getId(), email, TempRoles.getRoles());
            return new AuthenticatedUserInfo(email, token);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

}
