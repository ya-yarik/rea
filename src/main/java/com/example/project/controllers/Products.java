package com.example.project.controllers;



import com.example.project.repositories.CategoryRepository;
import com.example.project.repositories.GoodsRepository;
import com.example.project.services.CategoryServices;
import com.example.project.services.GoodsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Products {
        private final GoodsServices goodOperations;
        @Value("${upload.path}")
        private String uploadPath;

        private final GoodsRepository goodsRepository;
        private final CategoryRepository categoryRepository;

        private final CategoryServices categoryServices;

        @Autowired
        public Products(GoodsServices goodOperations, GoodsRepository goodsRepository, CategoryRepository categoryRepository, CategoryServices categoryServices) {
        this.goodOperations = goodOperations;
        this.goodsRepository = goodsRepository;
            this.categoryRepository = categoryRepository;
            this.categoryServices = categoryServices;
        }

    @GetMapping("/index/search")
    public String productSearchSimple(Model model) {
        model.addAttribute("productAll", goodOperations.getAllProducts());
            return "index";
    }


    @PostMapping("/index/search")
    public String productSearchSimple(@RequestParam("sort") String sortSubmit, Model model) {
            model.addAttribute("productS", goodOperations.getProductNameContainingIgnoreCase (sortSubmit));
        return "search";
    }

//    @GetMapping("/product/{id}")
//    public String infoProduct(@PathVariable("id") int id, Model model) {
//        model.addAttribute("product", goodOperations.getProductId(id));
//        return "product_info";
//    }
//    @GetMapping("/product/search")
//   public String productSearch (Model model) {
//    model.addAttribute("category", categoryRepository.findAll());
//    return "index";
//}
//
//    @PostMapping("/product/search")
//    public String productSearch(@RequestParam("search") String search, @RequestParam("up") String up, @RequestParam("to") String to, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "contract", required = false, defaultValue = "")String contract, Model model){
//        model.addAttribute("product", goodOperations.getAllProducts());
//
//        if(!up.isEmpty() & !to.isEmpty()){
//            if(!price.isEmpty()){
//                if(price.equals("sorted_by_ascending_price")) {
//                    if (!contract.isEmpty()) {
//                        if (contract.equals("furniture")) {
//                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 1));
//                        } else if (contract.equals("appliances")) {
//                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 3));
//                        } else if (contract.equals("clothes")) {
//                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 2));
//                        }
//                    } else {
//                        model.addAttribute("search_product", goodsRepository.findByNameOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to)));
//                    }
//                } else if(price.equals("sorted_by_descending_price")){
//                    if(!contract.isEmpty()){
//                        if(contract.equals("furniture")){
//                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 1));
//                        }else if (contract.equals("appliances")) {
//                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 3));
//                        } else if (contract.equals("clothes")) {
//                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 2));
//                        }
//                    }  else {
//                        model.addAttribute("search_product", goodsRepository.findByNameOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to)));
//                    }
//                }
//            } else {
//                model.addAttribute("search_product", goodsRepository.findByNameAndPriceGreaterThanEqualAndPriceLessThanEqual(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to)));
//            }
//        } else {
//            model.addAttribute("search_product", goodsRepository.findByNameContainingIgnoreCase(search));
//        }
//
//        model.addAttribute("value_search", search);
//        model.addAttribute("value_price_up", up);
//        model.addAttribute("value_price_to", to);
//        return "index";
//    }

//    @GetMapping("/user/product/fullcart")
//    public String getTemplateAddProductInCart(Model model){
//        model.addAttribute("cart", new Cart());
//        return "fullcart";
//    }
//
//    @PostMapping("/user/product/fullcart")
//    public String addProductInCart(@ModelAttribute("cart") Cart cart, HttpServletRequest request){
//        HttpSession session = request.getSession();
//
//        Cart cartSession = (Cart) session.getAttribute("cart");
//        cartSession = new Cart();
//        cartSession.setName(cart.getName());
//        cartSession.setPrice(cart.getPrice());
//        cartSession.setWeight(cart.getWeight());
//        session.setAttribute("cart", cartSession);
//        return "redirect:/fullcart";
//
//    }

//    @GetMapping("/product/research")
//    public String productSearch2 (Model model) {
//        model.addAttribute("productAll", goodOperations.getAllProducts());
//        return "search";
//    }
//    @PostMapping("/product/research")
//    public String productSearch2(@RequestParam("sort") String sortSubmit, Model model, @RequestParam("up") String up, @RequestParam("to") String to){
//            if(!up.isEmpty() && !to.isEmpty()){
//                model.addAttribute("value_search", sortSubmit);
//                model.addAttribute("value_price_up", up);
//                model.addAttribute("value_price_to", to);
//                model.addAttribute("ProductS", goodsRepository.findByNameAndPriceGreaterThanEqualAndPriceLessThanEqual(sortSubmit.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to)));
//        }
//        model.addAttribute("productS", goodOperations.getProductNameStartingWith(sortSubmit));
//        return "search";
//    }
//
//    @PostMapping("/product/error")
//    public String productSearch2(@RequestParam("search") String search, @RequestParam("up") String up, @RequestParam("to") String to, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "contract", required = false, defaultValue = "")String contract, Model model){
//        model.addAttribute("product", goodOperations.getAllProducts());
//
//        if(!up.isEmpty() & !to.isEmpty()){
//            if(!price.isEmpty()){
//                if(price.equals("sorted_by_ascending_price")) {
//                    if (!contract.isEmpty()) {
//                        if (contract.equals("furniture")) {
//                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 1));
//                        } else if (contract.equals("appliances")) {
//                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 3));
//                        } else if (contract.equals("clothes")) {
//                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 2));
//                        }
//                    } else {
//                        model.addAttribute("search_product", goodsRepository.findByNameOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to)));
//                    }
//                } else if(price.equals("sorted_by_descending_price")){
//                    if(!contract.isEmpty()){
//                        if(contract.equals("furniture")){
//                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 1));
//                        }else if (contract.equals("appliances")) {
//                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 3));
//                        } else if (contract.equals("clothes")) {
//                            model.addAttribute("search_product", goodsRepository.findByNameAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to), 2));
//                        }
//                    }  else {
//                        model.addAttribute("search_product", goodsRepository.findByNameOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to)));
//                    }
//                }
//            } else {
//                model.addAttribute("search_product", goodsRepository.findByNameAndPriceGreaterThanEqualAndPriceLessThanEqual(search.toLowerCase(), Float.parseFloat(up), Float.parseFloat(to)));
//            }
//        } else {
//            model.addAttribute("search_product", goodsRepository.findByNameContainingIgnoreCase(search));
//        }
//
//        model.addAttribute("value_search", search);
//        model.addAttribute("value_price_up", up);
//        model.addAttribute("value_price_to", to);
//        return "error";
//    }

}
