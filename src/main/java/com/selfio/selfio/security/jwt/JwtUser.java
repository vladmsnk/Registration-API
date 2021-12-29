package com.selfio.selfio.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

/**
 * The class allows using objects of {@link com.selfio.selfio.entities.User} as Spring User.
 */
public class JwtUser implements UserDetails {

    private final Integer id;
    private final String email;
    private final String password;
    private final boolean verified;
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * All args constructor.
     * @param id - user's id from relation.
     * @param email - user's email.
     * @param password - user's password.
     * @param verified - user's account verification status.
     * @param authorities user's authorities.
     */
    public JwtUser(Integer id, String email, String password, boolean verified, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.verified = verified;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isVerified() {
        return this.verified;
    }

    public Integer getId() {
        return this.id;
    }
}
