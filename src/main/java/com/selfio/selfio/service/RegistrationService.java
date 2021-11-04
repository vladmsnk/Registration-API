package com.selfio.selfio.service;

import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.email.EmailSenderService;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.UserRepository;
import com.selfio.selfio.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;
    private final JwtUtil jwtUtil;

    @Autowired
    public RegistrationService(UserService userService, UserRepository userRepository, EmailSenderService emailSenderService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
        this.jwtUtil = jwtUtil;
    }

    public void register(UserRegistrationDto userRegistrationDto) {

        if (userRepository.existsByEmail(userRegistrationDto.getEmail())) {
            throw new IllegalArgumentException("User with " + userRegistrationDto.getEmail() + "exits!");
        }
        User user = new User(userRegistrationDto.getLogin(),userRegistrationDto.getEmail(),userRegistrationDto.getPassword(), false);
        userService.saveUserWithEncodedPassword(user);
        String token = jwtUtil.generateToken(user);
        String emailLink = "<h1> <a href='http://localhost:8081/confirmation?token=" + token  + "'>Confirm Account</a> </h1>";
        emailSenderService.sendEmail(userRegistrationDto.getEmail(), emailLink);
    }

    public void confirmToken(String token) {
        User user = userRepository.findByLogin(jwtUtil.extractLogin(token));
        if (user == null) {
            throw new UsernameNotFoundException("Invalid token!");
        }
        if (jwtUtil.isTokenExpired(token)) {
            throw new IllegalStateException("Token is expired!");
        }
        user.setVerified(true);
        userService.saveUserWithEncodedPassword(user);
    }

}
