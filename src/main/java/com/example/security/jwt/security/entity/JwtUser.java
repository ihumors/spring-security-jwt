package com.example.security.jwt.security.entity;

import com.example.security.jwt.system.entity.User;
import com.example.security.jwt.system.enums.UserStatusEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author PD
 */
public class JwtUser implements UserDetails {

    private Integer userId;
    private String username;
    private String password;
    private boolean activated;
    private int status;

    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser() {
    }

    /**
     * 通过 user 对象创建jwtUser
     */
    public JwtUser(User user) {
        userId = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        activated = user.isActivated();
        status = user.getStatus().getCode();
        authorities = user.getRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    public Integer getUserId() {
        return userId;
    }

    public boolean isActivated() {
        return activated;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", activated=" + activated +
                ", status='" + status +
                ", authorities=" + authorities +
                '}';
    }

}
