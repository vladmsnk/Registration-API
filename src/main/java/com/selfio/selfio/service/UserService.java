package com.selfio.selfio.service;

import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.entities.User;
import com.selfio.selfio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    private  UserRepository userRepository;
    private  BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService() {

    }
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

    }

    public void saveUserWithEncodedPassword(User user) {
        String encoded = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        userRepository.save(user);
    }

    public User createUserByUseDTO(UserRegistrationDto userRegistrationDto) {
        return new User(userRegistrationDto.getLogin(), userRegistrationDto.getEmail(), userRegistrationDto.getPassword(), false);
    }


}
