package com.selfio.selfio.service;

import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.email.EmailSenderService;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;


@Service
public class RegistrationService {

    private final UserService userService;
    private final EmailSenderService emailSenderService;
    private final JwtUtil jwtUtil;

    @Autowired
    public RegistrationService(UserService userService, EmailSenderService emailSenderService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.emailSenderService = emailSenderService;
        this.jwtUtil = jwtUtil;
    }

    public String register(UserRegistrationDto userRegistrationDto) {
         String token =  userService.signUpUser(
                 new User(
                         userRegistrationDto.getLogin(),
                         userRegistrationDto.getEmail(),
                         userRegistrationDto.getPassword(),
                         false
                 )
         );

         String emailLink = "<h1> <a href='http://localhost:8080/registration/confirmation?token=" + token  + "'>Confirm Account</a> </h1>";
//         emailSenderService.sendEmail(userRegistrationDto.getEmail(), emailLink);
         return emailLink;
    }

    public String confirmToken(String token) {
        User user = userService.findUserByLogin(jwtUtil.extractLogin(token));
        if (user == null) {
            throw new UsernameNotFoundException("Invalid token!");
        }
        if (jwtUtil.isTokenExpired(token)) {
            throw new IllegalStateException("Token is expired!");
        }
        user.setVerified(true);
        userService.saveUser(user);
        return "success";
    }
}
