package com.selfio.selfio.security.jwt;

import com.selfio.selfio.service.JwtService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The class provides configuration for http requests filter.
 * @see org.springframework.security.config.annotation.SecurityConfigurerAdapter
 */
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * All parameter constructor.
     * @param jwtService is the custom Spring Service for tokens.
     * @param userDetailsService is the default Spring class for objects of {@link org.springframework.security.core.userdetails.UserDetails}
     */
    public JwtConfigurer(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Provides filter settings for http requests.
     * @param http - The object provides applying custom settings for http requests.
     */
    @Override
    public void configure(HttpSecurity http){
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtService, userDetailsService);
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
