package org.trishanku.oa.admin.jwtauthentication.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.trishanku.oa.admin.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class AssistUserDetails implements UserDetails {


    User user;

    public AssistUserDetails(User user) {
        this.user = user;
    }

    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach((role) -> log.info("role injected to the user is " + role.getName()));
        user.getRoles().forEach((role) -> authorities.add(new SimpleGrantedAuthority( role.getName())));
        return authorities;
    }

    @Override
    public String getPassword() {
        return "12345";
    }

    @Override
    public String getUsername() {
        return user.getEmailAddress();
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
        return user.isStatus();
    }
}
