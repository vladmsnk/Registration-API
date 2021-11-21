package com.selfio.selfio.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class AuthorizationConfig {


    private static final String APPLICATION_NAME = "GoogleserviceApplication";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    //private static final String CREDENTIALS_FILE_PATH = "com/selfio/selfio/googleCreds.json";
    private static final String CREDENTIALS_FILE_PATH = "googleCreds.json";
    private static final List<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/plus.login");

    @Bean
    public GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        GoogleAuthorizationCodeFlow.Builder builder = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT,
                JSON_FACTORY,
                "88455731347-5pfi558u1vm0mbsgnlq0g3sjd6dpq15f.apps.googleusercontent.com",
                "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",
                Arrays.asList("https://www.googleapis.com/auth/userinfo.email", "https://www.googleapis.com/auth/userinfo.profile")
        );
        return builder.build();
    }
}
