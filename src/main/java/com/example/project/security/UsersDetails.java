package com.example.project.security;

import com.example.project.models.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UsersDetails implements UserDetails {
    private final UserModel users;

    public UsersDetails(UserModel users) {
        this.users = users;
    }

    //возвращ. объект пользов-ля после успешной аутентиф.

    public UserModel getUser() {
        return this.users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(users.getRole()));
    }

    @Override
    public String getPassword() {
        return this.users.getPassword();
    }

    @Override
    public String getUsername() {
        return this.users.getLogin();
    }
    //данный метод отвечают за то, явл. ли аккаунт существующим
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //данный метод отвечают за то, явл. ли аккаунт блокированным
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //данный метод отвечают за то, явл. ли пароль действителен
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //данный метод отвечают за то, явл. ли аккаунт активным (или деактивирован)
    @Override
    public boolean isEnabled() {
        return true;
    }
}