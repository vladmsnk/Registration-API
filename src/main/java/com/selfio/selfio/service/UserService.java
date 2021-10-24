package com.selfio.selfio.service;


import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.entities.UserDataObject;
import com.selfio.selfio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDataObject save(UserRegistrationDto userRegistrationDto) {
        UserDataObject userDataObject = new UserDataObject(
                userRegistrationDto.getEmail(),
                userRegistrationDto.getPassword(),
                true
        );
        return userRepository.save(userDataObject);
    }
//    public List<UserDataObject> getUsers() {
//        return userRepository.findAll();
//    }

//    public Void processRegistration(UserDataObject userDataObject) {
//        userRepository.save(userDataObject);
//    }
}
