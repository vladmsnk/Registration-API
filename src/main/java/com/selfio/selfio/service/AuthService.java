package com.selfio.selfio.service;

import com.selfio.selfio.dto.AuthenticationDto;
import com.selfio.selfio.entities.AuthenticatedUserInfo;
import com.selfio.selfio.entities.TempRoles;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.UserRepository;
import com.selfio.selfio.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    public AuthenticatedUserInfo authenticateUser(AuthenticationDto requestInfo) throws UsernameNotFoundException, BadCredentialsException {
        try {
            String email = requestInfo.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestInfo.getPassword()));
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User not found");
            }
            String token = jwtTokenProvider.createToken(user.get().getId(), email, TempRoles.getRoles());
            return new AuthenticatedUserInfo(email, token);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

}
