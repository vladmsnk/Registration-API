package com.selfio.selfio.web;

import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.entities.UserDataObject;
import com.selfio.selfio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/registration")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//

//    @GetMapping
//    public List<UserDataObject> getUsers() {
//        return userService.getUsers();
//    }public String registerUserAccount(@ModelAttribute("user_data_object") UserRegistrationDto userRegistrationDto) {
////        userService.save(userRegistrationDto);
////        return "registration/success";
////    }

//    @GetMapping("")
//    public String showHomePage() {
//        return "index";
//    }
}
