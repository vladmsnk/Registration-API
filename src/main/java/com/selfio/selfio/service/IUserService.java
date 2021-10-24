package com.selfio.selfio.service;

import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.entities.UserDataObject;

public interface IUserService {
    UserDataObject registerNewUserAccount(UserRegistrationDto userRegistrationDto);
}
