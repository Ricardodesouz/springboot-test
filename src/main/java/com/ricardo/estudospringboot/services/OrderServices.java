package com.ricardo.estudospringboot.services;

import com.ricardo.estudospringboot.entities.Order;
import com.ricardo.estudospringboot.repositories.OrderRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServices {
    @Autowired
    private OrderRespository orderRespository;

    public List<Order> findAll(){
        return orderRespository.findAll();
    }
    public Order findById(Integer id ){
        Optional<Order> obj = orderRespository.findById(id);
        return obj.get();
    }
}
