package com.selfio.selfio.controllers;

import com.selfio.selfio.dto.UserAuth;
import com.selfio.selfio.services.GoogleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth/google/")
public class GoogleOauthController {

    private final GoogleAuthService googleAuthService;

    @Autowired
    public GoogleOauthController(GoogleAuthService googleAuthService) {
        this.googleAuthService = googleAuthService;
    }

    @GetMapping("login")
    public ResponseEntity<UserAuth> googleLogin(@RequestParam(name = "token") String token) throws IOException {

        return ResponseEntity.ok(googleAuthService.googleAuth(token));
    }
}
