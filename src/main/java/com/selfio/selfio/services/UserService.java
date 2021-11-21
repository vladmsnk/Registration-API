package com.selfio.selfio.services;

import com.selfio.selfio.domain.User;

public interface UserService {
    User findByEmail(String email);

    User findById(Integer id);
}
