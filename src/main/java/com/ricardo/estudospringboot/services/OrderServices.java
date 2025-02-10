package com.ricardo.estudospringboot.services;

import com.ricardo.estudospringboot.entities.Order;
import com.ricardo.estudospringboot.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServices {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll(){
        return orderRepository.findAll();
    }
    public Order findById(Integer id ){
        Optional<Order> obj = orderRepository.findById(id);
        return obj.get();
    }
}
