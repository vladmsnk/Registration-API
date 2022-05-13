package com.selfio.selfio.security.jwt;

import com.selfio.selfio.exceptions.ExpiredTokenException;
import com.selfio.selfio.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtTokenFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtService.resolveToken((HttpServletRequest) servletRequest);
        try {
            if (token != null && jwtService.validateToken(token)) {
                Authentication authentication = getAuthentication(jwtService.extractUserEmail(token));
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(HttpStatus.UNAUTHORIZED.value());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public Authentication getAuthentication(String email) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
