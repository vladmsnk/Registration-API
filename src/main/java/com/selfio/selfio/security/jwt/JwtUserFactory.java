package com.selfio.selfio.security.jwt;

import com.selfio.selfio.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * The class provides creation objects of {@link JwtUser} from user's info.
 */
public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    /**
     * The method provides
     * @param user is the object of entity 'users'.
     * @return New object with {@link org.springframework.security.core.userdetails.UserDetails} properties.
     */
    public static JwtUser create(User user) {
        return new JwtUser(user.getId(), user.getEmail(), user.getPassword(), user.getVerified(), mapToGrantedAuthorities());
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }
}
