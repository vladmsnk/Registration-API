package com.selfio.selfio.service;


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

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found!"));
    }

    public String singUpUser(User user) {
        boolean userExists = userRepository.findByEmail(user.getEmail())
                .isPresent();
        if (userExists) {
            throw new IllegalStateException("User already exists");
        }
        String encoded = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        //здесь будет отправка письма и подтверждение
        userRepository.save(user);
        return "registered";
    }
}
