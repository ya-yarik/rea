package com.example.project.security;

import com.example.project.services.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthenticationProviderProject implements AuthenticationProvider {
    private final UsersDetailsService usersDetailsService;

    @Autowired
    public AuthenticationProviderProject(UsersDetailsService usersDetailsService) {
        this.usersDetailsService = usersDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //Получ. логина с формы  аутентиф-ции. Спринг Секьюрити сам создаст объект формы и передаст его в объект authentication
        String login = authentication.getName();

        //обращаемся к usersDetailsService и вызываем метод loadUserByUsername передаём туда логин, кладё в объект юзер UserDetails
        UserDetails user = usersDetailsService.loadUserByUsername(login);

        //можно извлечь пароль из объекта аутентиф-ции
        String password = authentication.getCredentials().toString();

        //сравнение пароля, получ. из объекта аут-ции с паролем сохран.
        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Некорректный пароль");
        }

        //пользователь, пароль и коллекция ролей (роль  пользователя)
        return new UsernamePasswordAuthenticationToken(user, password, Collections.emptyList());
    }

        //указываетс для каких случаях примен. методы класса
        @Override
        public boolean supports (Class < ? > authentication){
            return false;
        }
}