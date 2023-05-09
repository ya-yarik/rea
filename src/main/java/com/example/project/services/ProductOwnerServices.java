package com.example.project.services;

import com.example.project.models.ProductOwner;
import com.example.project.repositories.ProductOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional(readOnly=true)
public class ProductOwnerServices {


        private final ProductOwnerRepository productOwnerRepository;

        @Autowired
        public ProductOwnerServices(ProductOwnerRepository productOwnerRepository) {
        this.productOwnerRepository = productOwnerRepository;
    }

        @Transactional
        public void newProductOwner (ProductOwner productOwner){
            productOwnerRepository.save(productOwner);
        }

        @Transactional
        public void deleteProductOwner(int id){
            productOwnerRepository.deleteById(id);
        }

        @Transactional
        public ProductOwner getProductOwnerId(int id){
            Optional<ProductOwner> thatProductOwner = productOwnerRepository.findById(id);
            return thatProductOwner.orElse(null);
        }
        @Transactional
        public List<ProductOwner> getAllProductOwners(){
            return productOwnerRepository.findAll();
        }
    }