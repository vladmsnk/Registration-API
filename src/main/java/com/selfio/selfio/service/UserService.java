package com.selfio.selfio.service;

import com.selfio.selfio.requests.UserRequest;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void saveUser(User user) {
        String encoded = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        userRepository.save(user);
    }

    public User createUserByRequest(UserRequest userRequest) {
        return new User(userRequest.getEmail(), userRequest.getPassword(), false);
    }

}
