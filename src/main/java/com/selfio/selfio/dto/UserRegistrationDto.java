package com.selfio.selfio.dto;

import com.selfio.selfio.validators.ValidEmail;
import com.selfio.selfio.validators.ValidPassword;

import java.util.Objects;

@ValidPassword
public class UserRegistrationDto {
    private String login;
    @ValidEmail
    private String email;
    private String password;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String email, String password, String login) {
        this.email = email;
        this.password = password;
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRegistrationDto{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistrationDto that = (UserRegistrationDto) o;
        return Objects.equals(login, that.login) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, email, password);
    }
}
