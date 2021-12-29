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

/**
 * User registration service
 */
@Service
public class RegistrationService {

    @Value("${confirmationLink}")
    private String confirmationLink;

    /**
     * Allows to set confirmation link.
     * @param confirmationLink is a custom confirmation link.
     */
    protected void setConfirmationLink(String confirmationLink) {
        this.confirmationLink = confirmationLink;
    }

    private final UserService userService;
    private final UserRepository userRepository;
    private final EmailSender emailSenderService;
    private final JwtService jwtService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Autowired constructor of the Service.
     * @param userService  is the custom Service for user entity based on Spring Boot.
     * @param userRepository is the Spring Repository of relation 'users'.
     * @param emailSenderService is the custom Service for email sending based on Spring Boot.
     * @param jwtService is the custom Spring Service for tokens.
     */
    @Autowired
    public RegistrationService(UserService userService, UserRepository userRepository, EmailSender emailSenderService, JwtService jwtService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
        this.jwtService = jwtService;
    }

    /**
     * Method for registering new user.
     * @param userRequest object with user credentials.
     * @return new object of {@link UserInfo}
     * @throws AlreadyExistsException - custom exception when user already registered.
     */
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

    /**
     * Method for JWT token confirmation.
     * @param token - recieved JWT token.
     * @return new object of {@link UserInfo}
     * @throws UserNotFoundException - custom exception when user not found in database.
     */
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


