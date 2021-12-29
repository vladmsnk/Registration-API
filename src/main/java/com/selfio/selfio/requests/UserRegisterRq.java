package com.selfio.selfio.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Objects of this class contains user's credentials, received from client.
 */
public class UserRegisterRq {

    @Email
    private String email;

    @NotNull(message = "password is required")
    @Size(min = 6, max = 16, message = "Password must be equal to or greater than 6 characters and less than 16 characters")
    private String password;

    public UserRegisterRq(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegisterRq that = (UserRegisterRq) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
