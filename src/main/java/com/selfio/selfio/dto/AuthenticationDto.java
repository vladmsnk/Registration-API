package com.selfio.selfio.dto;

import lombok.Data;

/**
 * The class describes the content of the authentication request.
 */
@Data
public class AuthenticationDto {
    private String email;
    private String password;
}
