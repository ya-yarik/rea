package com.example.project.controllers;
import com.example.project.repositories.GoodsRepository;
import com.example.project.services.GoodsServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Main {
    private final GoodsServices goodsServices;
    private final GoodsRepository goodsRepository;


    public Main(GoodsServices goodsServices, GoodsRepository goodsRepository) {
        this.goodsServices = goodsServices;
        this.goodsRepository = goodsRepository;
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
    public String productSearch3(){
        return "main";
    }

    @PostMapping("/main")
    public String productSearch3(@RequestParam("search") String search, @RequestParam("up") String up, @RequestParam("to") String to, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "contract", required = false, defaultValue = "")String contract, Model model){
        model.addAttribute("product", goodsServices.getAllProducts());

        if(!up.isEmpty() & !to.isEmpty()){
            if(!price.isEmpty()){
                if(price.equals("sorted_by_ascending_price")) {
                    if (!contract.isEmpty()) {
                        if (contract.equals("furniture")) {
                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 1));
                        } else if (contract.equals("appliances")) {
                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 3));
                        } else if (contract.equals("clothes")) {
                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 2));
                        }
                    } else {
                        model.addAttribute("search_product", goodsRepository.findByNameOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to)));
                    }
                } else if(price.equals("sorted_by_descending_price")){
                    if(!contract.isEmpty()){
                        if(contract.equals("furniture")){
                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 1));
                        }else if (contract.equals("appliances")) {
                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 3));
                        } else if (contract.equals("clothes")) {
                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 2));
                        }
                    }  else {
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

}