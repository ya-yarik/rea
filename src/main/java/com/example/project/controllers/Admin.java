package com.example.project.controllers;

import com.example.project.models.*;
import com.example.project.repositories.*;
import com.example.project.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class Admin {

    @Value("${upload.path}")
    private String uploadPath;
    private final UsersServices userOperations;
    private final CategoryRepository categoryRepository;
    private final GoodsServices goodsServices;
    private final CategoryServices categoryServices;
    private final WarehouseServices warehouseServices;
    private final ProductOwnerServices productOwnerServices;
    private final WarehouseRepository warehouseRepository;
    private final ProductOwnerRepository productOwnerRepository;
    private final OrderRepository orderRepository;
    private final OrdersServices ordersServices;

    @Autowired
    public Admin(UsersServices userOperations, CategoryRepository categoryRepository, GoodsServices goodsServices, CategoryServices categoryServices, WarehouseServices warehouseServices, ProductOwnerServices productOwnerServices, WarehouseRepository warehouseRepository, ProductOwnerRepository productOwnerRepository, OrderRepository orderRepository, OrdersServices ordersServices) {
        this.userOperations = userOperations;
        this.categoryRepository = categoryRepository;
        this.goodsServices = goodsServices;
        this.categoryServices = categoryServices;
        this.warehouseServices = warehouseServices;
        this.productOwnerServices = productOwnerServices;
        this.warehouseRepository = warehouseRepository;
        this.productOwnerRepository = productOwnerRepository;
        this.orderRepository = orderRepository;
        this.ordersServices = ordersServices;
    }

    @GetMapping("")
    public String admin() {
        return "admin";
    }


    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("userForm", userOperations.getAllUser());
        return "users";
    }

    @GetMapping("users/newuser")
    public String addUserC(Model model) {
        model.addAttribute("userForm", new UserModel());
        return "newuser";
    }

    @PostMapping("users/newuser")
    public String addUserC(@ModelAttribute("userForm") @Valid UserModel user, BindingResult scan, Model model, @RequestParam("file") MultipartFile multipartFile) {

        if (scan.hasErrors()) {
            return "newuser";
        }

        for (UserModel users :
                userOperations.getAllUser()) {
            if (users.getEmail().equals(user.getEmail())) {
                model.addAttribute("error", "Пользователь с таким e-mail уже существует");

                ObjectError error = new ObjectError("error", "Пользователь с таким e-mail уже существует");
                scan.addError(error);

                return "newuser";

            } else if (users.getPhone().equals(user.getPhone())) {
                model.addAttribute("error", "Пользователь с таким номером телефона уже существует");

                ObjectError error = new ObjectError("error", "Пользователь с таким номером телефона уже существует");
                scan.addError(error);

                return "newuser";
            }
        }

        if (multipartFile == null) {
            userOperations.addUserC(user);
        }
        else {
            File uploadDir = new File(uploadPath);//создание директории, куда будет загружаться файл, при условии, что пользователь решил загрузить картинку

            if (!uploadDir.exists()) {
                uploadDir.mkdir();//создание директории, если она не сущ.
            }
            String uuid = UUID.randomUUID().toString();//uuid - создаёт уникальное имя файла
            String resultFileName = uuid + " " + multipartFile.getOriginalFilename();

            try {
                multipartFile.transferTo(new File(uploadPath + resultFileName));//загрузка по описанному выше пути, наверху обработка исключения throw, если загрузка файла не произойдёт
            }

            catch (IOException e) {
                userOperations.addUserC(user);
            }

            user.setFilePic(resultFileName);//установление имени файла имени пользователя
        }

        userOperations.addUserC(user);

        return "redirect:/admin/users";
    }

    @GetMapping("users/{id}")
    public String aboutUser(Model model, @PathVariable("id") int id) {
        model.addAttribute("userForm", userOperations.getUserId(id));
        return "personaldata";
    }

    @GetMapping("users/edit/{id}")
    public String editUser(Model model, @PathVariable("id") int id) {
        model.addAttribute("userForm", userOperations.getUserId(id));
        return "edituser";
    }

    @PostMapping("users/edit/{id}")
    public String editUser(@ModelAttribute("userForm") @Valid UserModel user, BindingResult scanEdit, @PathVariable("id") int id, @RequestParam("file") MultipartFile multipartFile) {

        if (scanEdit.hasErrors()) {
            return "edituser";
        }

        if (multipartFile == null) {
            userOperations.editUser(id, user);
        }

        else {
            File uploadDir = new File(uploadPath);//создание директории, куда будет загружаться файл, при условии, что пользователь решил загрузить картинку

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();//создание директории, если она не сущ.
                }

            String uuid = UUID.randomUUID().toString();//uuid - создаёт уникальное имя файла
            String resultFileName = uuid + " " + multipartFile.getOriginalFilename();

            try {
                multipartFile.transferTo(new File(uploadPath + resultFileName));//загрузка по описанному выше пути, наверху обработка исключения throw, если загрузка файла не произойдёт
            }

            catch (IOException e) {
                userOperations.editUser(id, user);
            }

            user.setFilePic(resultFileName);//установление имени файла имени пользователя
        }

        userOperations.editUser(id, user);
        return "redirect:/admin/users/" + id;
    }

    @GetMapping("users/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userOperations.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/product/sortgood")
    public String sortingAndSearchingGoods() {
        return "sortgood";
    }

    @PostMapping("/product/sortgood")
    public String sortingAndSearchingGoods(@RequestParam("sortgood") String sorting, @RequestParam("sort") String sortSubmit, Model model) {

        if (sorting.equals("name")) {
            model.addAttribute("goodT", goodsServices.getProductNameContainingIgnoreCase(sortSubmit));
        }

        else if (sorting.equals("priceAsc")) {
            model.addAttribute("goodT", goodsServices.findByNameOrderByPriceAsc(sortSubmit));
        }

        else if (sorting.equals("priceDesc")) {
            model.addAttribute("goodT", goodsServices.findByNameOrderByPriceDesc(sortSubmit));
        }

        return "sortgood";
    }

    @GetMapping("users/sortuser")
    public String sortingAndSearching() {
        return "sortuser";
    }

    @PostMapping("users/sortuser")
    public String sortingAndSearching(@RequestParam("sort_filter") String sorting, @RequestParam("sort") String sortSubmit, Model model) {

        if (sorting.equals("email")) {
            model.addAttribute("usersT", userOperations.getUserEmail(sortSubmit));
        }

        else if (sorting.equals("phone")) {
            model.addAttribute("usersT", userOperations.getUserPhone(sortSubmit));
        }

        else if (sorting.equals("surname")) {
            model.addAttribute("usersT", userOperations.getUserSurnameOrderByAge(sortSubmit));
        }

        else if (sorting.equals("surnameDesc")) {
            model.addAttribute("usersT", userOperations.findBySurnameOrderByAgeDesc(sortSubmit));
        }

        else if (sorting.equals("surnameStart")) {
            model.addAttribute("usersT", userOperations.getUserSurnameStartingWith(sortSubmit));
        }

        return "sortuser";
    }

    @GetMapping("/product/add")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        model.addAttribute("warehouse", warehouseRepository.findAll());
        model.addAttribute("productOwner", productOwnerRepository.findAll());
        return "add_product";
    }

    @PostMapping("/product/add")
    public String newProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one") MultipartFile file_one, @RequestParam("category") int category, @RequestParam("warehouse") int warehouse, @RequestParam("productOwner") int productOwner, Model model) {

        Category category_db = (Category) categoryRepository.findById(category).orElseThrow();
        Warehouse warehouse_db = (Warehouse) warehouseRepository.findById(warehouse).orElseThrow();
        ProductOwner productowner_db = (ProductOwner) productOwnerRepository.findById(productOwner).orElseThrow();

        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryRepository.findAll());
            model.addAttribute("warehouse", warehouseRepository.findAll());
            model.addAttribute("productOwner", productOwnerRepository.findAll());
            return "add_product";
        }

        if (file_one != null) {
            File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();

            try {
                file_one.transferTo(new File(uploadPath + "/" + resultFileName));
                ProductPhotos image = new ProductPhotos();
                image.setProduct(product);
                image.setFileName(resultFileName);
                product.addImageToProduct(image);
            }

            catch (IOException e) {
                return "add_product";
            }
        }

        goodsServices.newProduct(product, category_db);
        return "redirect:/admin/product";
    }

    @GetMapping("/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id) {
        model.addAttribute("product", goodsServices.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        model.addAttribute("warehouse", warehouseRepository.findAll());
        model.addAttribute("productOwner", productOwnerRepository.findAll());
        return "edit_product";
    }

    @GetMapping("/product/setcategory")
    public String newCategory(Model model) {
        model.addAttribute("setcategory", new Category());
        return "setcategory";
    }

    @PostMapping("/product/setcategory")
    public String newCategory(@ModelAttribute("setcategory") @Valid Category category, Model model) {

        model.addAttribute("setcategory", new Category());
        categoryServices.newCategory(category);

        return "redirect:/admin/product/category";
    }

    @GetMapping("/product/delete/category/{id}")
    public String deleteCategory(@PathVariable("id") int id) {
        categoryServices.deleteCategory(id);
        return "redirect:/admin/product/category";
    }

    @GetMapping("/product/category/{id}")
    public String aboutCategory(Model model, @PathVariable("id") int id) {
        model.addAttribute("category", categoryServices.getCategoryId(id));
        return "categorydata";
    }

    @GetMapping("/product/category")
    public String allCategories(Model model) {
        model.addAttribute("category", categoryServices.getAllCategories());
        return "category";
    }

    @GetMapping("/product/setwarehouse")
    public String newWarehouse(Model model) {
        model.addAttribute("warehouse", new Warehouse());
        return "/product/setwarehouse";
    }

    @PostMapping("/product/setwarehouse")
    public String newWarehouse(@ModelAttribute("warehouse") @Valid Warehouse warehouse, Model model) {

        model.addAttribute("warehouse", new Warehouse());
        warehouseServices.newWarehouse(warehouse);

        return "redirect:/admin/product/warehouse";
    }

    @GetMapping("/product/delete/warehouse/{id}")
    public String deleteWarehouse(@PathVariable("id") int id) {
        warehouseServices.deleteWarehouse(id);
        return "redirect:/admin/product/warehouse";
    }

    @GetMapping("/product/warehouse/{id}")
    public String aboutWarehouse(Model model, @PathVariable("id") int id) {
        model.addAttribute("warehouse", warehouseServices.getWarehouseId(id));
        return "/product/warehousedata";
    }

    @GetMapping("/product/warehouse")
    public String allWarehouses(Model model) {
        model.addAttribute("warehouse", warehouseServices.getAllWarehouses());
        return "/product/warehouse";
    }

    @GetMapping("/product/setproductowner")
    public String newProductOwner(Model model) {
        model.addAttribute("product_owner", new ProductOwner());
        return "/product/setproductowner";
    }

    @PostMapping("/product/setproductowner")
    public String newProductOwner(@ModelAttribute("product_owner") @Valid ProductOwner productOwner, Model model) {

        model.addAttribute("product_owner", new ProductOwner());
        productOwnerServices.newProductOwner(productOwner);

        return "redirect:/admin/product/productowner";
    }

    @GetMapping("/product/delete/productowner/{id}")
    public String deleteProductOwner(@PathVariable("id") int id) {
        productOwnerServices.deleteProductOwner(id);
        return "redirect:/admin/product/productowner";
    }

    @GetMapping("/product/productowner/{id}")
    public String aboutProductOwner(Model model, @PathVariable("id") int id) {
        model.addAttribute("product_owner", productOwnerServices.getProductOwnerId(id));
        return "/product/productownerdata";
    }

    @GetMapping("/product/productowner")
    public String allProductOwners(Model model) {
        model.addAttribute("product_owner", productOwnerServices.getAllProductOwners());
        return "/product/productowner";
    }

    //получить список всех продуктов и вернуть html страницу
    @GetMapping("/product")
    public String index(Model model) {
        model.addAttribute("productAll", goodsServices.getAllProducts());
        return "product";
    }

    // получить объект из листа по id
    //PathVariable извлекает переменные
    @GetMapping("/product/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", goodsServices.getProductId(id));
        return "product_info";
    }

    //принять редактируемый объект с формы и обновить информацию о редактируемом товаре
    @PostMapping("/product/edit/{id}")
    public String editProduct(@ModelAttribute("edit_product") @Valid Product product, BindingResult scanGoodsEditFields, @PathVariable("id") int id, @RequestParam("file_two") MultipartFile file_two, @RequestParam("file_three") MultipartFile file_three, @RequestParam("file_four") MultipartFile file_four, @RequestParam("file_five") MultipartFile file_five, Model model) {

        if (scanGoodsEditFields.hasErrors()) {

            model.addAttribute("category", categoryRepository.findAll());
            model.addAttribute("warehouse", warehouseRepository.findAll());
            model.addAttribute("productOwner", productOwnerRepository.findAll());

            return "product/edit_product";
        }

        if (file_two != null) {
            File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_two.getOriginalFilename();

            try {
                file_two.transferTo(new File(uploadPath + "/" + resultFileName));
                ProductPhotos image = new ProductPhotos();
                image.setProduct(product);
                image.setFileName(resultFileName);
                product.addImageToProduct(image);
            }

            catch (IOException e) {
                goodsServices.editProduct(id, product);
            }
        }

        if (file_three != null) {
            File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_three.getOriginalFilename();

            try {
                file_three.transferTo(new File(uploadPath + "/" + resultFileName));
                ProductPhotos image = new ProductPhotos();
                image.setProduct(product);
                image.setFileName(resultFileName);
                product.addImageToProduct(image);
            }

            catch (IOException e) {
                goodsServices.editProduct(id, product);
            }
        }

        if (file_four != null) {
            File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_four.getOriginalFilename();

            try {
                file_four.transferTo(new File(uploadPath + "/" + resultFileName));
            }

            catch (IOException e) {
                goodsServices.editProduct(id, product);
                ProductPhotos image = new ProductPhotos();
                image.setProduct(product);
                image.setFileName(resultFileName);
                product.addImageToProduct(image);
            }
        }

        if (file_five != null) {
            File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_five.getOriginalFilename();

            try {
                file_five.transferTo(new File(uploadPath + "/" + resultFileName));
            }

            catch (IOException e) {
                goodsServices.editProduct(id, product);
                ProductPhotos image = new ProductPhotos();
                image.setProduct(product);
                image.setFileName(resultFileName);
                product.addImageToProduct(image);
            }
        }

        goodsServices.editProduct(id, product);
        return "redirect:/admin/product";
    }

    @GetMapping("/order/{id}")
    public String aboutOrder(Model model, @PathVariable("id") int id) {
        model.addAttribute("order", ordersServices.aboutOrders(id));
        return "order";
    }

    @GetMapping("/orders")
    public String allOrders(Model model) {
        model.addAttribute("order", ordersServices.allOrders());
        return "orderlist";
    }

    @GetMapping("/order/edit/{id}")
    public String orderStatus(Model model, @PathVariable("id") int id) {

        model.addAttribute("order", ordersServices.aboutOrders(id));
        model.addAttribute("statuses", orderRepository.findAll());

        return "order";
    }

    @PostMapping("/order/edit/{id}")
    public String orderStatus(@ModelAttribute("order") @Valid Orders orders, @PathVariable("id") int id) {
        ordersServices.orderStatus(id, orders);
        return "redirect:/admin/orders";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        goodsServices.deleteProduct(id);
        return "redirect:/admin/product";
    }

    @GetMapping("/user/changerole/{id}")
    public String changeRole(Model model, @PathVariable("id") int id) {
        model.addAttribute("userForm", userOperations.getUserId(id));
        return "personaldata";
    }

    @PostMapping("/user/changerole/{id}")
    public String changeRole(@ModelAttribute("userForm") @Valid UserModel user, BindingResult scanEdit, @PathVariable("id") int id, @RequestParam("file") MultipartFile multipartFile) {
        if (scanEdit.hasErrors()) {
            return "personaldata";
        }

        if (multipartFile == null) {
            userOperations.changeRole(id, user);
        }

        else {

            File uploadDir = new File(uploadPath);//создание директории, куда будет загружаться файл, при условии, что пользователь решил загрузить картинку

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();//создание директории, если она не сущ.
                }

            String uuid = UUID.randomUUID().toString();//uuid - создаёт уникальное имя файла
            String resultFileName = uuid + " " + multipartFile.getOriginalFilename();

            try {
                multipartFile.transferTo(new File(uploadPath + resultFileName));//загрузка по описанному выше пути, наверху обработка исключения throw, если загрузка файла не произойдёт
            }

            catch (IOException e) {
                userOperations.changeRole(id, user);
            }
            user.setFilePic(resultFileName);//установление имени файла имени пользователя
        }

        userOperations.changeRole(id, user);
        return "redirect:/admin/users/" + id;
    }

    @GetMapping("/orders/search")
    public String orderSearch() {
        return "orderlist";
    }


    @PostMapping("/orders/search")
    public String orderSearch(@RequestParam("sort_filter") String sorting, @RequestParam("sort") String sortSubmit, Model model) {

        if (sorting.equals("numberEnding")) {
            model.addAttribute("orderS", ordersServices.getOrderNumberEndingWith(sortSubmit));
        }

        else if (sorting.equals("empty")) {
            model.addAttribute("orderS", ordersServices.allOrders());
        }

        return "orderlist";
    }
}