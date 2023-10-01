package com.server.security;

import com.server.model.User;
import com.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.swing.text.html.Option;
import java.util.Optional;

@Configuration
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // mapping and check authentication in database
        final String username = authentication.getName();

        if (username.isEmpty()) {
            throw new UsernameNotFoundException("User " + authentication.getName() + " not found");
        }

        UserDetails user = null;
        try {
            user = userDetailsService.loadUserByUsername(username);
        }
        catch (UsernameNotFoundException ex) {
            throw new BadCredentialsException("invalid login details");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches((CharSequence) authentication.getCredentials(),user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user.getUsername(), authentication.getCredentials(), user.getAuthorities() );
        }
        else {
            throw new BadCredentialsException("wrong password");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
