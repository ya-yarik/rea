package com.example.project.controllers;
import com.example.project.models.*;
import com.example.project.repositories.GoodsRepository;
import com.example.project.util.ProductErrorResponse;
import com.example.project.util.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class Api {

    private final GoodsRepository goodsRepository;

    public Api(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @GetMapping("/info")
    public String getInfoApi(){
        return "Информация о товарах магазина (API)";
    }

    @GetMapping("/index")
    public List<Product> getProduct(){
        System.out.println(goodsRepository.findAll());
        return goodsRepository.findAll();
    }

    @GetMapping("/product/{id}")
    public Product getProductId(@PathVariable("id") int id){
        Optional<Product> productOptional = goodsRepository.findById(id);
        return productOptional.orElseThrow(ProductNotFoundException::new);
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProductId(@PathVariable("id") int id){
        goodsRepository.deleteById(id);
        return "Товар удалён";
    }

    @GetMapping("/admin/product/add")
    public String addProduct (@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("price") float price, @RequestParam("weight") float weight, Category category, Warehouse warehouse,  ProductOwner productOwner, @RequestParam("producer") String producer, List<ProductPhotos> imageList){

        Product newProduct = new Product(name, price, weight, description, warehouse, productOwner, producer, category, imageList);
        goodsRepository.save(newProduct);
        return "Товар добавлен";
    }


    @ExceptionHandler
    public ResponseEntity<ProductErrorResponse> handlerException(ProductNotFoundException productNotFoundException){
        ProductErrorResponse response = new ProductErrorResponse("Не удалось найти товар по данному id");
        // NOT_FOUND - СТАТУС В ЗАГОЛОВКЕ 404, RESPONSE - ТЕЛО ОТВЕТА
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}