package com.example.project.services;

import com.example.project.models.Category;
import com.example.project.models.Product;
import com.example.project.repositories.GoodsRepository;
import com.example.project.repositories.GoodsRepositorySecond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly=true)
public class GoodsServices {
    private GoodsRepository goodsRepository;
    private GoodsRepositorySecond goodsRepositorySecond;

    @Autowired
    public GoodsServices(GoodsRepository goodsRepository){
        this.goodsRepository=goodsRepository;
    }

    public GoodsServices(GoodsRepositorySecond goodsRepositorySecond){
        this.goodsRepositorySecond=goodsRepositorySecond;
    }

    public List<Product> getAllProducts(){
        return goodsRepository.findAll();
    }
    public Product getProductId(int id){
        Optional<Product> thatProduct = goodsRepository.findById(id);
        return thatProduct.orElse(null);
    }

    @Transactional
    public void newProduct (Product product, Category category){
        product.setCategory(category);
        goodsRepository.save(product);
    }

    @Transactional
    public void editProduct(int id, Product product){
        product.setId(id);
        goodsRepository.save(product);
    }

    @Transactional
    public void deleteProduct(int id){
        goodsRepository.deleteById(id);
    }

    ////////////
    public List<Product> getProductNameContainingIgnoreCase (String sortSubmit){
        return goodsRepositorySecond.findByNameContainingIgnoreCase(sortSubmit);
    }
//    public List<Product> findByProvider(Provider provider){
//        return goodsRepository.findByProvider(provider);
//    }

    public List<Product> findByNameOrderByPriceAsc (String name){
        return goodsRepository.findByNameOrderByPriceAsc (name);
    }

    public List<Product> findByNameOrderByPriceDesc (String name){
        return goodsRepository.findByNameOrderByPriceDesc (name);
    }
}