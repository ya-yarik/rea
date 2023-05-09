package com.example.project.services;

import com.example.project.models.Orders;
import com.example.project.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrdersServices {
    private final OrderRepository orderRepository;

    @Autowired
    public OrdersServices(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Orders> allOrders(){
        return orderRepository.findAll();
    }

    public Orders aboutOrders (int id){
        Optional<Orders> order = orderRepository.findById(id);
        return order.orElse(null);
    }
    @Transactional
    public void orderStatus (int id, Orders orders){
        orders.setId(id);
        orderRepository.save(orders);
    }

    public List<Orders> getOrderNumberEndingWith (String endingWith){
        return orderRepository.findByNumberEndingWith(endingWith);
    }

}
