package com.selfio.selfio.service;

import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.entities.User;

public interface IUserService {
    User registerNewUserAccount(UserRegistrationDto userRegistrationDto);
}
