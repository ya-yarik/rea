package com.example.project.controllers;

import com.example.project.models.Avtorization;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Authentication {

    @GetMapping("/auth")
    public String login(){
        return "auth";
    }

//    @GetMapping("/auth")
//    public String auth(Model model) {
//        model.addAttribute("auth", new Avtorization());
//        return "auth";
//    }

}