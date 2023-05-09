package com.example.project.controllers;

import com.example.project.services.GoodsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Products {
    private final GoodsServices goodOperations;

    @Autowired
    public Products(GoodsServices goodOperations) {
        this.goodOperations = goodOperations;
    }

    @GetMapping("/index/search")
    public String productSearchSimple(Model model) {
        model.addAttribute("productAll", goodOperations.getAllProducts());
        return "index";
    }

    @PostMapping("/index/search")
    public String productSearchSimple(@RequestParam("sort") String sortSubmit, Model model) {
        model.addAttribute("productS", goodOperations.getProductNameContainingIgnoreCase(sortSubmit));
        return "search";
    }
}