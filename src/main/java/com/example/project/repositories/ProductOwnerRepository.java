package com.example.project.repositories;

import com.example.project.models.ProductOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOwnerRepository extends JpaRepository<ProductOwner, Integer> {

}
