package com.selfio.selfio.service;

import com.selfio.selfio.dto.AuthenticationDto;
import com.selfio.selfio.entities.AuthenticatedUserInfo;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User authentication service
 */
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    /**
     * Autowired constructor
     * @param authenticationManager is the default Spring bean for authentication.
     * @param jwtService is the custom Spring Service for tokens.
     * @param userRepository is the Spring Repository of relation 'users'.
     */
    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       JwtService jwtService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    /**
     * The method authenticates user by credentials.
     * @param requestInfo object of class {@link AuthenticationDto}
     * @return {@link AuthenticatedUserInfo}
     * @throws UsernameNotFoundException is the Spring Boot default exception
     * @throws BadCredentialsException is the Spring Boot default exception
     */
    public AuthenticatedUserInfo authenticateUser(AuthenticationDto requestInfo) throws UsernameNotFoundException, BadCredentialsException {
        try {
            String email = requestInfo.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestInfo.getPassword()));
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User not found");
            }
            String token = jwtService.createToken(user.get());
            return new AuthenticatedUserInfo(email, user.get().getVerified(), token);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

}
