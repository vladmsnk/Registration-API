package com.selfio.selfio.user;

public class User {
    private Integer id;
    private String email;
    private String password;
    private Boolean verified;

    public User(Integer id, String email, String password, Boolean verified) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.verified = verified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}