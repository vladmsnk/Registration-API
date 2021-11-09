package com.selfio.selfio.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    public static BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
