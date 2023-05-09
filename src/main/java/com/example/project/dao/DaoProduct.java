package com.example.project.dao;

import com.example.project.models.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DaoProduct {
    private int id;
    private List<Product> products = new ArrayList<>();

    // метод позволяет добавить объект в лист
    public void addProduct(Product product){
        products.add(product);
        product.setId(++id);
    }

    // метод позволяет получить все товары из ArrayList
    public List<Product> getProducts() {
        return products;
    }

    // метод позволяет получить объект из листа по id

    public Product getProductId(int id){
        //создаём переменную pr и перебираем лист массива  product
        //getId() - метод, получающий id
//        for (Product pr: products) {
//            if(pr.getId() == id){
//                return pr;
//            }
//        }
//        return null;

        //другой вариант через лямбда выражение: метод стрим API, фильтрация по id, (в скобках аналог циклу for each), findAny возвращает любой продукт соответствующий заданному id, иначе вернётся ноль
        return products.stream().filter(product -> product.getId() == id).findAny().orElse(null);
    }

    // метод позволяет найти товар по id и заменить поля данного товара на значения, которые придут вместе с объектом формы
    public void updateProduct(int id, Product product) {
        Product productUpdate = getProductId(id); // получаем редактируемый объект по его id
        productUpdate.setName(product.getName());
        productUpdate.setPrice(product.getPrice());
        productUpdate.setWeight(product.getWeight());
        productUpdate.setProvider(product.getProvider());
    }

    // метод позволяет удалить товар по id
    public void deleteProduct(int id){
        products.removeIf(product -> product.getId() == id);
    }
}