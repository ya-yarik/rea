package com.example.project.repositories;

import com.example.project.enumm.Provider;
import com.example.project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Product, Integer> {
    List<Product> findByNameStartingWith(String startingWith);
//    List<Product> findByProvider(Provider provider);
    List<Product> findByNameOrderByPriceAsc (String name);
    List<Product> findByNameOrderByPriceDesc (String name);

}