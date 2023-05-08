package com.example.project.controllers;

import com.example.project.models.Cart;
import com.example.project.models.Orders;
import com.example.project.models.Product;
import com.example.project.models.UserModel;
import com.example.project.repositories.CartRepository;
import com.example.project.repositories.GoodsRepository;
import com.example.project.repositories.OrderRepository;
import com.example.project.security.UsersDetails;
import com.example.project.services.GoodsServices;
import com.example.project.services.UsersServices;
import com.example.project.util.UsersValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class Users {
    @Value("${upload.path}")
    private String uploadPath;
    private final UsersValidator usersValidator;
    private final UsersServices usersServices;
    private final GoodsServices goodsServices;
    private final GoodsRepository goodsRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;



    public Users(UsersValidator usersValidator, UsersServices usersServices, GoodsRepository goodsRepository, GoodsServices goodsServices, CartRepository cartRepository, OrderRepository orderRepository) {
        this.usersValidator = usersValidator;
        this.usersServices = usersServices;
        this.goodsRepository = goodsRepository;
        this.goodsServices=goodsServices;
        this.cartRepository=cartRepository;
        this.orderRepository=orderRepository;
    }

    @GetMapping("/profile")
    public String profile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal();
        String role = usersDetails.getUser().getRole();
        if(role.equals("ROLE_ADMIN")){
            return "redirect:/admin";
        }

        model.addAttribute("productAll", goodsServices.getAllProducts());
        return "/profile";
    }


    @GetMapping("/registration")
    public String registration(@ModelAttribute("users") UserModel users){
        return "registration";
    }

    @PostMapping("/registration")
    public String resultRegistration(@ModelAttribute("users") @Valid UserModel users, BindingResult bindingResult){
        usersValidator.validate(users, bindingResult);
        if(bindingResult.hasErrors()){
            return "registration";
        }
        usersServices.register(users);
        return "redirect:/profile";
    }

//    @GetMapping("profile/edit")
//    public String editUser(@ModelAttribute ("userForm") @Valid UserModel userModel, Model model, @PathVariable("id") int id) {
//        for (UserModel users :
//                usersServices.getAllUser()) {
//            if (users.getId().equals(userModel.getId()))
//                model.addAttribute("userForm");
//        }
//        return "change";
//    }
//
//    @PostMapping("profile/edit")
//    public String editUser(@ModelAttribute("userForm") @Valid UserModel user, BindingResult scanEdit, @RequestParam("file") MultipartFile multipartFile, Model model){
//
//        usersValidator.validate(user, scanEdit);
//           if(scanEdit.hasErrors()){
//        return "change";
//    }
//
//        for (UserModel users :
//                usersServices.getAllUser()) {
//            if (users.getEmail().equals(user.getEmail())) {
//                model.addAttribute("error", "Пользователь с таким e-mail уже существует");
//
//                ObjectError error = new ObjectError("error", "Пользователь с таким e-mail уже существует");
//                scanEdit.addError(error);
//
//                return "change";
//            } else if (users.getPhone().equals(user.getPhone())) {
//                model.addAttribute("error", "Пользователь с таким номером телефона уже существует");
//
//                ObjectError error = new ObjectError("error", "Пользователь с таким номером телефона уже существует");
//                scanEdit.addError(error);
//
//                return "change";
//            }
//        }
//
//        if (multipartFile == null) {
//            usersServices.editUserUser(user);
//        } else {
//            File uploadDir = new File(uploadPath);//создание директории, куда будет загружаться файл, при условии, что пользователь решил загрузить картинку
//            if (!uploadDir.exists()) {
//                uploadDir.mkdir();//создание директории, если она не сущ.
//            }
//            String uuid = UUID.randomUUID().toString();//uuid - создаёт уникальное имя файла
//            String resultFileName = uuid + " " + multipartFile.getOriginalFilename();
//            try {
//                multipartFile.transferTo(new File(uploadPath + resultFileName));//загрузка по описанному выше пути, наверху обработка исключения throw, если загрузка файла не произойдёт
//            } catch (IOException e) {
//                usersServices.editUserUser(user);
//            }
//            user.setFilePic(resultFileName);//установление имени файла имени пользователя
//        }
//
//        usersServices.editUserUser(user);
//        return "redirect:/profile";
//    }


    @GetMapping("/profile/cart/add/{id}")
    public String addProductInCart(@PathVariable("id") int id, Model model){
        // Получаем продукт по id
        Product product = goodsServices.getProductId(id);
        // Извлекаем объект аутентифицированного пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal();
        // Извлекаем id пользователя из объекта
        int id_person = usersDetails.getUser().getId();
        Cart cart = new Cart(id_person, product.getId());
        cartRepository.save(cart);
        return "redirect:/profile/cart";
    }

    @GetMapping("/profile/cart")
    public String cart(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal();
        // Извлекаем id пользователя из объекта
        int id_person = usersDetails.getUser().getId();

        List<Cart> cartList = cartRepository.findByPersonId(id_person);
        List<Product> productList = new ArrayList<>();

        // Получаем продукты из корзины по id товара
        for (Cart cart: cartList) {
            productList.add(goodsServices.getProductId(cart.getProductId()));
        }

        // Вычисление итоговой цена
        float price = 0;
        for (Product product: productList) {
            price += product.getPrice();
        }

        model.addAttribute("price", price);
        model.addAttribute("cart_product", productList);
        return "cart";
    }

    @GetMapping("/profile/cart/delete/{id}")
    public String deleteProductFromCart(Model model, @PathVariable("id") int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal();
        // Извлекаем id пользователя из объекта
        int id_person = usersDetails.getUser().getId();
        List<Cart> cartList = cartRepository.findByPersonId(id_person);
        List<Product> productList = new ArrayList<>();

        // Получаем продукты из корзины по id товара
        for (Cart cart: cartList) {
            productList.add(goodsServices.getProductId(cart.getProductId()));
        }
        cartRepository.deleteCartByProductId(id);
        return "redirect:/profile/cart";
    }

    @GetMapping("/profile/order/create")
    public String order(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal();
        // Извлекаем id пользователя из объекта
        int id_person = usersDetails.getUser().getId();

        List<Cart> cartList = cartRepository.findByPersonId(id_person);
        List<Product> productList = new ArrayList<>();

        // Получаем продукты из корзины по id товара
        for (Cart cart: cartList) {
            productList.add(goodsServices.getProductId(cart.getProductId()));
        }

        // Вычисление итоговой цена
        float price = 0;
        for (Product product: productList) {
            price += product.getPrice();
        }

        String uuid = UUID.randomUUID().toString();
        for(Product product : productList){
            Orders newOrder = new Orders(uuid, product, usersDetails.getUser(), 1, product.getPrice(), com.example.project.enumm.Statuses.Оформлен);
            orderRepository.save(newOrder);
            cartRepository.deleteCartByProductId(product.getId());
        }
        return "redirect:/profile/orders";
    }

    @GetMapping("/profile/orders")
    public String orderUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal();
        List<Orders> orderList = orderRepository.findByUsers(usersDetails.getUser());
        model.addAttribute("orders", orderList);
        return "orders";
    }

//    @GetMapping("/profile/change")
//    public String editUser(Model model) {
//        model.addAttribute("userForm");
//        return "change";
//    }
//
//    @PostMapping("/profile/change/{id}")
//    public String editUser(@ModelAttribute("userForm") @Valid UserModel user, BindingResult scanEdit, @PathVariable("id") int id, Model model, @RequestParam("file") MultipartFile multipartFile) {
//        if (scanEdit.hasErrors()) {
//            return "change";
//        }
//
//        for (UserModel users:
//                usersServices.getAllUser()){
//            if(users.getEmail().equals(user.getEmail())){
//                model.addAttribute("error", "Пользователь с таким e-mail уже существует");
//
//                ObjectError error = new ObjectError("error", "Пользователь с таким e-mail уже существует");
//                scanEdit.addError(error);
//
//                return "change";
//            }
//            else if (users.getPhone().equals(user.getPhone())){
//                model.addAttribute("error", "Пользователь с таким номером телефона уже существует");
//
//                ObjectError error = new ObjectError("error", "Пользователь с таким номером телефона уже существует");
//                scanEdit.addError(error);
//
//                return "change";
//            }
//        }
//
//        if (multipartFile == null) {usersServices.editUser(id, user);}
//
//        else{
//            File uploadDir = new File(uploadPath);//создание директории, куда будет загружаться файл, при условии, что пользователь решил загрузить картинку
//            if(!uploadDir.exists()){
//                uploadDir.mkdir();//создание директории, если она не сущ.
//            }
//            String uuid = UUID.randomUUID().toString();//uuid - создаёт уникальное имя файла
//            String resultFileName = uuid+" "+ multipartFile.getOriginalFilename();
//            try {
//                multipartFile.transferTo(new File(uploadPath + resultFileName));//загрузка по описанному выше пути, наверху обработка исключения throw, если загрузка файла не произойдёт
//            } catch (IOException e) {
//                usersServices.editUser(id, user);
//            }
//            user.setFilePic(resultFileName);//установление имени файла имени пользователя
//        }
//
//        usersServices.editUser(id, user);
//        return "redirect:/profile";
//    }

//    @GetMapping("/profile/delete/{id}")
//    public String deleteUser(UserModel users) {
//        usersServices.deleteUser(users);
//        return "redirect:/index";
//    }

    @GetMapping("/profile/mainsearch")
    public String productSearch(){
        return "mainsearch";
    }

    @PostMapping("/profile/mainsearch")
    public String productSearch(@RequestParam("search") String search, @RequestParam("up") String up, @RequestParam("to") String to, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "category", required = false) String category, Model model){
//        Category categoryB = (Category) categoryRepository.findById(category).orElseThrow();
        model.addAttribute("product", goodsServices.getAllProducts());

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
        return "mainsearch";
    }

    @GetMapping("/profile/product/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", goodsServices.getProductId(id));
        return "product_user";
    }
    @GetMapping("/profile/search")
    public String productSearchUserSimple(Model model) {
        model.addAttribute("productAll", goodsServices.getAllProducts());
        return "profile";
    }


    @PostMapping("/profile/search")
    public String productSearchUserSimple(@RequestParam("sort") String sortSubmit, Model model) {
        model.addAttribute("productS", goodsServices.getProductNameContainingIgnoreCase(sortSubmit));
        return "usersearch";
    }
}
