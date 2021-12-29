package com.selfio.selfio.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The class provides beans for Services.
 */
@Configuration
public class ServiceConfiguration {

    /**
     * The method creates bean of password encoder.
     * @return object of encoder.
     */
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
