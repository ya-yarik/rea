package com.example.project.controllers;
import com.example.project.models.Category;
import com.example.project.repositories.CategoryRepository;
import com.example.project.repositories.GoodsRepository;
import com.example.project.services.GoodsServices;
import org.springframework.beans.*;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.Field;
import java.sql.Wrapper;
import java.util.Map;

@Controller
public class Main {
    private final GoodsServices goodsServices;
    private final GoodsRepository goodsRepository;
    private final CategoryRepository categoryRepository;


    public Main(GoodsServices goodsServices, GoodsRepository goodsRepository, CategoryRepository categoryRepository) {
        this.goodsServices = goodsServices;
        this.goodsRepository = goodsRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/index")
    public String indexes(Model model) {
        model.addAttribute("productAll", goodsServices.getAllProducts());
        return "index";
    }

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("productAll", goodsServices.getAllProducts());
        return "index";
    }

    @GetMapping("/product/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", goodsServices.getProductId(id));
        return "product_guests";

    }

    @GetMapping("/main")
    public String productSearch3(Model model){
        model.addAttribute("category", categoryRepository.findAll());
        return "main";
    }

    @PostMapping("/main")
    public String productSearch3(@RequestParam("search") String search, @RequestParam("up") String up, @RequestParam("to") String to, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "category", required = false) String category, Model model){
        model.addAttribute("product", goodsServices.getAllProducts());
//        Category categoryB = (Category) categoryRepository.findById(category).orElseThrow();

        if(!up.isEmpty() & !to.isEmpty()){
            if(!price.isEmpty()){
                if(price.equals("sorted_by_ascending_price")) {
                    if (!category.isEmpty()) {
                        model.addAttribute("category", category);
                        model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), Integer.parseInt(category)));
                    } else {
                        model.addAttribute("search_product", goodsRepository.findByNameOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to)));
                    }
                } else if(price.equals("sorted_by_descending_price")){
                    if(!category.isEmpty()){
                            model.addAttribute("category", category);
                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), Integer.parseInt(category)));
                        }

                         else {
                        model.addAttribute("search_product", goodsRepository.findByNameOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to)));
                    }
                }
            } else {
                model.addAttribute("search_product", goodsRepository.findByNameAndPriceGreaterThanEqualAndPriceLessThanEqual(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to)));
            }
        } else {
            model.addAttribute("search_product", goodsRepository.findByNameContainingIgnoreCase(search));
        }

        model.addAttribute("value_search", search);
        model.addAttribute("value_price_up", up);
        model.addAttribute("value_price_to", to);
        return "main";
    }

//    @GetMapping("/experiment")
//    public String productSearch4(Model model){
//        model.addAttribute("category", categoryRepository.findAll());
//        return "experiment";
//    }

    @GetMapping("/experiment")
    public String productSearch4(Model model){
        model.addAttribute("category", categoryRepository.findAll());
        return "experiment";
    }

    @PostMapping("/experiment")
    public String productSearch4(@RequestParam("search") String search, @RequestParam(value = "categories", required = false) Integer category, Model model){
//        Category category = (Category) categoryRepository.findById(categories).orElseThrow();
        model.addAttribute("category", categoryRepository.findAll());
//        model.addAttribute("search_product", goodsServices.getByNameAndCategory(search.toLowerCase(), category));
        model.addAttribute("search_product", goodsServices.getByNameAndCategory(search.toLowerCase(), category));
        return "experiment";
    }

}