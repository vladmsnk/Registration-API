package com.selfio.selfio.dto;

import com.selfio.selfio.validators.ValidEmail;
import com.selfio.selfio.validators.ValidPassword;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;


public class UserRegistrationDto {

    @NotEmpty
    @NonNull
    private String login;

    @NotEmpty
    @NonNull
    @ValidEmail
    private String email;

    @NotEmpty
    @NonNull
    @ValidPassword
    private String password;

    private boolean enabled;

    public UserRegistrationDto(String email, String password, String login) {
        this.email = email;
        this.password = password;
        this.login = login;
    }

    public UserRegistrationDto() {

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
