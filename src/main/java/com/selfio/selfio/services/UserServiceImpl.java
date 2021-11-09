package com.selfio.selfio.services;

import com.selfio.selfio.entities.User;
import com.selfio.selfio.repositories.UserRepository;
import com.selfio.selfio.security.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = PasswordEncoder.getPasswordEncoder();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
}
