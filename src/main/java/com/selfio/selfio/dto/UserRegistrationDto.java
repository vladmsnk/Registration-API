package com.selfio.selfio.dto;

public class UserRegistrationDto {
    private String email;
    private String password;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
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
}
