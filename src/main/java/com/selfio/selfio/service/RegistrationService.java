package com.selfio.selfio.service;

import com.selfio.selfio.entities.UserInfo;
import com.selfio.selfio.exceptions.AlreadyExistsException;
import com.selfio.selfio.exceptions.ExpiredTokenException;
import com.selfio.selfio.exceptions.UserNotFoundException;
import com.selfio.selfio.requests.UserRegisterRq;
import com.selfio.selfio.email.EmailSender;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class RegistrationService {

    @Value("${confirmationLink}")
    private String confirmationLink;

    protected void setConfirmationLink(String confirmationLink) {
        this.confirmationLink = confirmationLink;
    }

    private final UserService userService;
    private final UserRepository userRepository;
    private final EmailSender emailSenderService;
    private final JwtService jwtService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public RegistrationService(UserService userService, UserRepository userRepository, EmailSender emailSenderService, JwtService jwtService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserInfo register(UserRegisterRq userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new AlreadyExistsException("User with " + userRequest.getEmail() + " exists!");
        }
        User user = userService.createUserByRequest(userRequest);
        userService.saveUser(user);
        String token = jwtService.createToken(user);
        emailSenderService.sendEmail(userRequest.getEmail(), String.format(confirmationLink, token));
        return new UserInfo(
                user.getEmail(),
                user.getVerified()
        );
    }

    public UserInfo confirmToken(String token) {
        if (jwtService.isTokenExpired(token)) {
            throw new ExpiredTokenException("Token is expired!");
        }
        Integer id   = jwtService.extractUserId(token);
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with id = " + id + " was not found!");
        }
        User user = userOptional.get();
        user.verify();
        userRepository.save(user);

        return new UserInfo(user.getEmail(), user.getVerified());
    }
}


