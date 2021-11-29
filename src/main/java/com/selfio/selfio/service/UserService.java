package com.selfio.selfio.service;

import com.selfio.selfio.entities.User;

public interface UserService{
    User findByEmail(String email);
    User findById(Integer id);
}
