package com.ssafy.live.account.auth.jwt;

import com.ssafy.live.account.user.service.CustomUserDetailService;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UsersProvider implements AuthenticationProvider {

    @Autowired
    CustomUserDetailService userDetailsService;

    @SuppressWarnings("unchecked")
    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        String userId = authentication.getName();
        String userPassword = authentication.getCredentials().toString(); //userPassword

        UserDetails user = userDetailsService.loadUserByUsername(userId);
        if (!user.isEnabled()) {
            throw new BadCredentialsException(user.getUsername());
        }
        return new UsernamePasswordAuthenticationToken(user.getUsername(), userPassword,
            user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public class UserToken extends UsernamePasswordAuthenticationToken {

        private static final long serialVersionUID = 1L;

        @Getter
        @Setter
        UserDetails user;

        public UserToken(Object principal, Object credentials,
            Collection<? extends GrantedAuthority> authorities, UserDetails user) {
            super(principal, credentials, user.getAuthorities());
            this.user = user;
            //principal : userId
            //credentials : password
        }

        @Override
        public Object getDetails() {
            return user;
        }
    }
}
