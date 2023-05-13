package com.example.project.controllers;
import com.example.project.repositories.CategoryRepository;
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

    @GetMapping("/aboutus")
    public String aboutUs() {
        return "aboutus";
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
    public String productSearch3(@RequestParam("search") String search, @RequestParam("up") String up, @RequestParam("to") String to, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "categoriest", required = false) String categoriest, Model model){

        model.addAttribute("value_search", search);
        model.addAttribute("value_price_up", up);
        model.addAttribute("value_price_to", to);
        model.addAttribute("product", goodsServices.getAllProducts());
        model.addAttribute("category", categoryRepository.findAll());

        if(!up.isEmpty() & !to.isEmpty()){
            if(price.isEmpty() & categoriest.matches("\\d+")) {
                    model.addAttribute("categoriest", categoriest);
                    model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), Integer.parseInt(categoriest)));
            }
            else if(!price.isEmpty()) {
                if (price.equals("sorted_by_ascending_price")) {
                    ///

                        if (categoriest.matches("\\d+")) {
                            model.addAttribute("categoriest", categoriest);
                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), Integer.parseInt(categoriest)));

                        } else {

                            model.addAttribute("search_product", goodsRepository.findByNameOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to)));
                        }
                    } else if (price.equals("sorted_by_descending_price")) {
                        if (categoriest.matches("\\d+")) {
                            model.addAttribute("categoriest", categoriest);
                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), Integer.parseInt(categoriest)));

                        } else {

                        model.addAttribute("search_product", goodsRepository.findByNameOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to)));
                    }
                }
            }


            else {

                model.addAttribute("search_product", goodsRepository.findByNameAndPriceGreaterThanEqualAndPriceLessThanEqual(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to)));
            }
        }

        //
        else if(up.isEmpty() & to.isEmpty() & !price.isEmpty() & categoriest.matches("\\d+")){

            if (price.equals("sorted_by_ascending_price")) {
                if (!categoriest.isEmpty()) {
                    model.addAttribute("categoriest", categoriest);
                    model.addAttribute("search_product", goodsRepository.findByNameAndCategoryAndPriceOrderByPriceAsc(search.toLowerCase(), Integer.parseInt(categoriest)));

                } else {
                    model.addAttribute("search_product", goodsRepository.findByNameOrderByPriceAsc(search.toLowerCase()));

                }
            } else if (price.equals("sorted_by_descending_price")) {
                if (!categoriest.isEmpty()) {
                    model.addAttribute("categoriest", categoriest);
                    model.addAttribute("search_product", goodsRepository.findByNameAndCategoryAndPriceOrderByPriceDesc(search.toLowerCase(), Integer.parseInt(categoriest)));

                } else {
                    model.addAttribute("search_product", goodsRepository.findByNameOrderByPriceDesc(search.toLowerCase()));

                }
            }
        }
        //

        ////
        else if(up.isEmpty() & to.isEmpty() & price.isEmpty() & categoriest.matches("\\d+")){
            model.addAttribute("categoriest", categoriest);
            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryAndPriceOrderByPriceAsc(search.toLowerCase(), Integer.parseInt(categoriest)));
        }
        ////

        else {

            model.addAttribute("search_product", goodsRepository.findByNameContainingIgnoreCase(search));
        }
        return "main";
    }
    /////////////////////////////Разрабатываемое
    @GetMapping("/experiment")
    public String productSearch4(Model model){
        model.addAttribute("category", categoryRepository.findAll());
        return "experiment";
    }

    @PostMapping("/experiment")
    public String productSearch4(@RequestParam("search") String search, @RequestParam(value = "category", required = false) String category, Model model){
        model.addAttribute("value_search", search);
        //model.addAttribute("product", goodsServices.getAllProducts());
//        Category category = (Category) categoryRepository.findById(categories).orElseThrow();
//        model.addAttribute("category", categoryRepository.findAll());
//        model.addAttribute("search_product", goodsServices.getByNameAndCategory(search.toLowerCase(), category));
        model.addAttribute("category", category);
//        model.addAttribute("categories", categoryServices.getCategoryId(categories));
        model.addAttribute("search_product", goodsRepository.findByNameAndCategory(search.toLowerCase(), Integer.parseInt(category)));
        return "experiment";
    }
}