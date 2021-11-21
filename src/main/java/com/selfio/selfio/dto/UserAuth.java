package com.selfio.selfio.dto;

public class UserAuth {
    private final String token;
    private final String firstName;
    private final String secondName;
    private final String email;
    private final Boolean verified;

    public UserAuth(String token, String firstName, String secondName, String email, Boolean verified) {
        this.token = token;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.verified = verified;
    }

    public static class Builder {

        private String token;
        private String firstName;
        private String secondName;
        private String email;
        private Boolean verified;

        public Builder withToken(String token) {
            this.token = token;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withSecondName(String secondName) {
            this.secondName = secondName;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withVerified(Boolean verified) {
            this.verified = verified;
            return this;
        }

        public UserAuth build() {
            return new UserAuth(token, firstName, secondName, email, verified);
        }
    }

    public String getToken() {
        return token;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getVerified() {
        return verified;
    }
}
