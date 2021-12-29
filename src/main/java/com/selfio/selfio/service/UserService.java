package com.selfio.selfio.service;

import com.selfio.selfio.requests.UserRegisterRq;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for interaction with {@link UserRepository}
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Autowired constructor.
     * @param userRepository is the Spring Repository of relation 'users'.
     * @param bCryptPasswordEncoder is the object of.
     */
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Method for saving user in relation 'users'.
     * @param user user is object of entity 'users'.
     */
    public void saveUser(User user) {
        String encoded = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        userRepository.save(user);
    }

    public User createUserByRequest(UserRegisterRq userRequest) {
        return new User(userRequest.getEmail(), userRequest.getPassword(), false);
    }

}
