package com.example.project.repositories;


import com.example.project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepositorySecond extends JpaRepository<Product, Integer> {
    List<Product> findByNameContainingIgnoreCase(String sortSubmit);
}