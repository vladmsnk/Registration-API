package com.selfio.selfio.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;


public class UserRegistrationDto {

    @NotNull(message = "Login can not be null")
    @Size(min = 2, message = "Login must be at least two characters")
    private String login;

    @Email
    private String email;

    @NotNull(message = "password is required")
    @Size(min = 6, max = 16, message = "Password must be equal to or greater than 6 characters and less than 16 characters")
    private String password;


    public UserRegistrationDto(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    @Override
//    public String toString() {
//        return
//    }

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
