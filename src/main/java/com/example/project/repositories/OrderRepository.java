package com.example.project.repositories;

import com.example.project.models.Orders;
import com.example.project.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByUsers(UserModel users);

    List<Orders> findByNumberEndingWith(String endingWith);

}