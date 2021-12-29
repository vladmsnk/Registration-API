package com.selfio.selfio.entities;

import lombok.Getter;

/**
 * The class describes the content of the authentication response.
 */
@Getter
public class AuthenticatedUserInfo {

    private final String email;
    private final boolean verified;
    private final String token;

    public AuthenticatedUserInfo(String email, boolean verified, String token) {
        this.email = email;
        this.verified = verified;
        this.token = token;
    }
}
