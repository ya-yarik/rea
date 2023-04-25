package com.example.project.util;

import com.example.project.models.Users;
import com.example.project.services.UsersServices;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UsersValidator implements Validator {

    private final UsersServices usersService;

    public UsersValidator(UsersServices usersService) {
        this.usersService = usersService;
    }

    // В данном методу указываем для какой модели предназначен данный валидатор
    @Override
    public boolean supports(Class<?> clazz) {
        return Users.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Users users = (Users) target;
        if(usersService.findByLogin(users) != null){
            errors.rejectValue("login", "", "Данный логин уже зарегистрирован в системе");
        }
    }
}