package com.selfio.selfio.service;

import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.UserRepository;
import com.selfio.selfio.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

    }

    public String signUpUser(User user) {
        boolean userExists = userRepository.findByEmail(user.getEmail())
                .isPresent();
        if (userExists) {
            throw new IllegalStateException("User already exists");
        }
        String encoded = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        String jwtToken = jwtUtil.generateToken(user);
        userRepository.save(user);
        return jwtToken;
    }

    public User findUserByLogin(String login) {
        return  userRepository.findByLogin(login);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

}
