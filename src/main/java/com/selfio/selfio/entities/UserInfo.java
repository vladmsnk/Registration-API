package com.selfio.selfio.entities;

public class UserInfo {

    private String email;
    private boolean verified;

    public UserInfo(String email, boolean verified) {
        this.email = email;
        this.verified = verified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
