package com.selfio.selfio.services;

import com.selfio.selfio.entities.User;

public interface UserService {
    User findByEmail(String email);

    User findById(Integer id);
}
