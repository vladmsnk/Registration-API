package com.selfio.selfio.config;

import com.selfio.selfio.entities.User;
import com.selfio.selfio.repo.UsersDetailsRepo;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/").permitAll()
                .anyRequest().permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor(UsersDetailsRepo usersDetailsRepo) {
        return map -> {
            String email = (String) map.get("email");
            String googleID = (String) map.get("sub");
            User user = usersDetailsRepo.findByEmail(email).orElseGet(() -> {
                User newUser = usersDetailsRepo.findByGoogleID(googleID).orElseGet(() -> {
                    User newUser2 = new User();
                    newUser2.setGoogleID(googleID);
                    newUser2.setFirstName((String) map.get("given_name"));
                    newUser2.setSecondName((String) map.get("family_name"));
                    newUser2.setEmail(email);
                    newUser2.setVerified((Boolean) map.get("email_verified"));
                    return newUser2;
                });
                return newUser;
            });

            if (user.getGoogleID() == null) {
                user.setGoogleID((String) map.get("sub"));
            }
            if (user.getFirstName() == null) {
                user.setFirstName((String) map.get("given_name"));
            }
            if (user.getFirstName() == null) {
                user.setSecondName((String) map.get("family_name"));
            }
            if (user.getEmail() == null) {
                user.setEmail((String) map.get("email"));
            }
            if (user.getVerified() == null) {
                user.setVerified((Boolean) map.get("email_verified"));
            }

            usersDetailsRepo.save(user);
            return user;
        };
    }
}
/*
                newUser.setName((String) map.get("name"));
                newUser.setGender((String) map.get("gender"));
                newUser.setLocale((String) map.get("locale"));
                newUser.setUserpic((String) map.get("picture"));
 */