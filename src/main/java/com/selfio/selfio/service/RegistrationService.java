package com.selfio.selfio.service;

import com.selfio.selfio.errors.AlreadyExistsException;
import com.selfio.selfio.errors.ExpiredTokenException;
import com.selfio.selfio.errors.UserNotFoundException;
import com.selfio.selfio.requests.UserRequest;
import com.selfio.selfio.email.EmailSenderService;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;
    private final JwtService jwtService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public RegistrationService(UserService userService, UserRepository userRepository, EmailSenderService emailSenderService, JwtService jwtService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
        this.jwtService = jwtService;
    }

    public User register(UserRequest userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new AlreadyExistsException("User with " + userRequest.getEmail() + " exists!");
        }
        User user = userService.createUserByRequest(userRequest);
        userService.saveUser(user);
        String token = jwtService.generateToken(user);
        String emailLink = "<h1> <a href='http://localhost:8081/confirmation?token=" + token  + "'>Confirm Account</a> </h1>";
        emailSenderService.sendEmail(userRequest.getEmail(), emailLink);
        return user;
    }

    public User confirmToken(String token) {
        Integer id   = jwtService.extractId(token);
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with id = " + id + " was not found!");
        }
        if (jwtService.isTokenExpired(token)) {
            throw new ExpiredTokenException("Token is expired!");
        }
        User user = userRepository.findById(id).get();
        user.verify();
        userRepository.save(user);
        return user;
    }
}


