package com.Task.Manager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class TaskUserDetail implements UserDetails {
    private final TaskUser user;
    private Collection<SimpleGrantedAuthority> authorities;

    public TaskUserDetail(TaskUser user){
        this.user = user;
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_"+ user.getRole()));
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

}
