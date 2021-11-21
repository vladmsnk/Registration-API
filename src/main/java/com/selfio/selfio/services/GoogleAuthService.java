package com.selfio.selfio.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.selfio.selfio.domain.User;
import com.selfio.selfio.dto.UserAuth;
import com.selfio.selfio.repo.UsersDetailsRepo;
import com.selfio.selfio.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class GoogleAuthService {
    private final UsersDetailsRepo usersDetailsRepo;
    private final GoogleAuthorizationCodeFlow codeFlow;
    private final JwtTokenProvider tokenProvider;


    @Autowired
    public GoogleAuthService(UsersDetailsRepo usersDetailsRepo, GoogleAuthorizationCodeFlow codeFlow, JwtTokenProvider tokenProvider) {
        this.usersDetailsRepo = usersDetailsRepo;
        this.codeFlow = codeFlow;
        this.tokenProvider = tokenProvider;
    }

    public UserAuth googleAuth(String idTokenString) throws IOException {

        GoogleIdToken token = GoogleIdToken.parse(JacksonFactory.getDefaultInstance(), idTokenString);
        GoogleIdToken.Payload payload = token.getPayload();

//        boolean isTokenVerify = token.verify();
        String firstName = payload.get("given_name").toString();
        String secondName = payload.get("family_name").toString();
        String email = payload.getEmail();
        String googleID = payload.getSubject();


        User user = usersDetailsRepo.findByEmail(email).orElseGet(() -> {
            User newUser = usersDetailsRepo.findByGoogleID(googleID).orElseGet(() -> {
                User newUser2 = new User();
                newUser2.setGoogleID(googleID);
                newUser2.setFirstName(firstName);
                newUser2.setSecondName(secondName);
                newUser2.setEmail(email);
                newUser2.setVerified(true);
                return newUser2;
            });
            return newUser;
        });

        if (user.getGoogleID() == null) {
            user.setGoogleID(googleID);
        }
        if (user.getFirstName() == null) {
            user.setFirstName(firstName);
        }
        if (user.getFirstName() == null) {
            user.setSecondName(secondName);
        }
        if (user.getEmail() == null) {
            user.setEmail(email);
            user.setVerified(true);
        }

        usersDetailsRepo.save(user);

        // jwt token
        String authToken = tokenProvider.createToken(email);

        UserAuth userAuth = new UserAuth.Builder()
                .withEmail(email)
                .withFirstName(firstName)
                .withSecondName(secondName)
                .withVerified(true)
                .withToken(authToken)
                .build();

        return userAuth;
    }
}
// pro spring 5