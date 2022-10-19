package com.practice.todolist.security.services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "UserInfo", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "password")
})

public class UserDetailsImpl implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String createdTimeStamp;
    private String updatedTimeStamp;

    public UserDetailsImpl(String email, String password) {
        super();
        this.email = email;
        this.password = password;
        SimpleDateFormat formatter = new SimpleDateFormat();
        this.createdTimeStamp = formatter.format(new Date());
        this.updatedTimeStamp = formatter.format(new Date());
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUpdatedTimeStamp(String updatedTime) {
        this.updatedTimeStamp = updatedTime;
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
        return Collections.singleton(new SimpleGrantedAuthority("ADMIN"));
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return email;
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
