package com.selfio.selfio.entities.transfer;

import com.selfio.selfio.validators.ValidEmail;

import javax.validation.constraints.*;

public class AuthUserDataObject {
    @Size(min = 8, max = 50)
    @ValidEmail
    private String userEmail;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 25)
    private String userPassword;

    public AuthUserDataObject(String email, String password) {
        userEmail = email;
        userPassword = password;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
