package com.example.project.controllers;

import com.example.project.models.Avtorization;
import com.example.project.models.Users;
import com.example.project.security.UsersDetails;
import com.example.project.services.UsersServices;
import com.example.project.util.UsersValidator;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Main {
    private final UsersValidator usersValidator;
    private final UsersServices usersServices;

    public Main(UsersValidator usersValidator, UsersServices usersServices) {
        this.usersValidator = usersValidator;
        this.usersServices = usersServices;
    }

    @GetMapping("")
    public String main() {
        return "index";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("users") Users users){
        return "registration";
    }

    @PostMapping("/registration")
    public String resultRegistration(@ModelAttribute("users") @Valid Users users, BindingResult bindingResult){
        usersValidator.validate(users, bindingResult);
        if(bindingResult.hasErrors()){
            return "registration";
        }
        usersServices.register(users);
        return "redirect:/index";
    }
}