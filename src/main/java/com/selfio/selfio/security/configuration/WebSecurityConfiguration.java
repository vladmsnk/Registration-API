package com.selfio.selfio.security.configuration;

import com.selfio.selfio.security.jwt.JwtConfigurer;
import com.selfio.selfio.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * The class annotated by Spring Configuration and EnableWebSecurity. Provides configuring of http requests.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private final static String LOGIN_ENDPOINT = "/api/v1/auth/**";

    /**
     * Autowired constructor.
     * @param tokenProvider is the object of {@link JwtService}.
     * @param userDetailsService object of default Spring class {@link UserDetailsService}
     */
    @Autowired
    public WebSecurityConfiguration(JwtService tokenProvider, UserDetailsService userDetailsService) {
        this.jwtService = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    /**
     *
     * @return default Spring bean of {@link AuthenticationManager}
     * @throws Exception if bean was not created.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Provides http security settings.
     * @param http is the object of {@link HttpSecurity}.
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/register/registration").permitAll()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers("/api/v1/register/confirmation")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtService, userDetailsService));
    }
}
