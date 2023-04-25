package com.example.project.controllers;



import com.example.project.models.Cart;
import com.example.project.models.Product;
import com.example.project.services.GoodsServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class Products {
        private final GoodsServices goodOperations;
        @Value("${upload.path}")
        private String uploadPath;

    @Autowired
        public Products(GoodsServices goodOperations) {
        this.goodOperations = goodOperations;
    }

    // Данный метод позволяет получить список всех продуктов и вернуть html страницу
    @GetMapping("/product")
    public String index(Model model){
        model.addAttribute("product", goodOperations.getAllProducts());
        return "product";
    }

    // Данный метод позволяет получить объект из листа по id
    //PathVariable позволяет извлекать переменные
    @GetMapping("/product/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("product", goodOperations.getProductId(id));
        return "product_info";

    }

    // Данный метод позволяет отобразить представление с формой подабвления товара
    @GetMapping("/product/add")
    public String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "add_product";
    }

    // Данный метод позволяет принять данные с формы и сохранить товар в лист
    @PostMapping("/product/add")
    public String newProduct(@ModelAttribute("product") @Valid Product product, BindingResult scanGoodsAddFields, @RequestParam("file") MultipartFile file) {
        if (scanGoodsAddFields.hasErrors()){
            return "add_product";
        }

        if(file == null){
            goodOperations.newProduct(product);
        }

        else{
            File uploadDir = new File(uploadPath);//создание директории, куда будет загружаться файл, при условии, что пользователь решил загрузить картинку
            if(!uploadDir.exists()){
                uploadDir.mkdir();//создание директории, если она не сущ.
            }
            String uuid = UUID.randomUUID().toString();//uuid - создаёт уникальное имя файла
            String resultFileName = uuid+" "+ file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadPath+resultFileName));//загрузка по описанному выше пути, наверху обработка исключения throw, если загрузка файла не произойдёт
            } catch (IOException e) {
                goodOperations.newProduct(product);
            }
            product.setFilePic(resultFileName);//установление имени файла имени товара
        }

        goodOperations.newProduct(product);
        return "redirect:/product";

    }

    // Данный метод позволяет получить редактируемый продукт по id и вернуть форму редактирования продукта
    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("edit_product", goodOperations.getProductId(id));
        return "edit_product";/*переадресация на url*/
    }

    // Данный метод позволяет принять редактируемый объект с формы и обновить информацион о редактируемом товаре
    @PostMapping("/product/edit/{id}")
    public String editProduct(@ModelAttribute("edit_product") @Valid Product product, BindingResult scanGoodsEditFields, @PathVariable("id") int id, @RequestParam("file") MultipartFile file) {
       if(scanGoodsEditFields.hasErrors()){
           return "edit_product";
       }
        if(file == null){goodOperations.editProduct(id,product);}
        else {
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuid = UUID.randomUUID().toString();
            String resultFileName = uuid+" "+ file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadPath + resultFileName));
            } catch (IOException e) {
                goodOperations.editProduct(id,product);
            }
            product.setFilePic(resultFileName);
        }
        goodOperations.editProduct(id,product);
        return "redirect:/product";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        goodOperations.deleteProduct(id);
        return "redirect:/product";
    }

    ////
    @GetMapping("/product/sortgood")
    public String sortingAndSearchingGoods(){
        return "sortgood";
    }

    @PostMapping("/product/sortgood")
    public String sortingAndSearchingGoods(@RequestParam("sortgood") String sorting, @RequestParam("sort") String sortSubmit, Model model){

        if (sorting.equals("name")){
            model.addAttribute("goodT", goodOperations.getProductNameStartingWith(sortSubmit));
        }

        else if(sorting.equals("priceAsc")) {
            model.addAttribute("goodT", goodOperations.findByNameOrderByPriceAsc(sortSubmit));
        }
        else if(sorting.equals("priceDesc")) {
            model.addAttribute("goodT", goodOperations.findByNameOrderByPriceDesc(sortSubmit));
        }
//        else if(sorting.equals("provider")){
//            model.addAttribute("goodT", goodOperations.findByProvider(provider));
//        }
        return "sortgood";
    }

    @GetMapping("/cart")
    public String getCart(Model model){
        return "cart";
    }

    @GetMapping("/user/product/fullcart")
    public String getTemplateAddProductInCart(Model model){
        model.addAttribute("cart", new Cart());
        return "fullcart";
    }

    @PostMapping("/user/product/fullcart")
    public String addProductInCart(@ModelAttribute("cart") Cart cart, HttpServletRequest request){
        HttpSession session = request.getSession();

        Cart cartSession = (Cart) session.getAttribute("cart");
        cartSession = new Cart();
        cartSession.setName(cart.getName());
        cartSession.setPrice(cart.getPrice());
        cartSession.setQuantity(cart.getQuantity());
        session.setAttribute("cart", cartSession);
        return "redirect:/cart";

    }

}
