package com.practice.todolist.security.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.practice.todolist.dto.UserInfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String email;
    private String password;
    private String createdTimeStamp;
    private String updatedTimeStamp;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(Optional<UserInfo> user) {
        return new UserDetailsImpl(user.get());

    }

    public UserDetailsImpl(UserInfo user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.createdTimeStamp = user.getCreatedTimeStamp();
        this.updatedTimeStamp = user.getUpdatedTimeStamp();
        /* this.authorities = Arrays.asList(new SimpleGrantedAuthority("ADMIN")); */
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public String getUpdatedTimeStamp() {
        return updatedTimeStamp;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public final int hashCode() {
        int result = 17;
        if (id != null) {
            result = 31 * result + id.hashCode();
        }
        if (email != null) {
            result = 31 * result + email.hashCode();
        }
        if (password != null) {
            result = 31 * result + password.hashCode();
        }
        if (createdTimeStamp != null) {
            result = 31 * result + createdTimeStamp.hashCode();
        }
        if (updatedTimeStamp != null) {
            result = 31 * result + updatedTimeStamp.hashCode();
        }

        return result;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
