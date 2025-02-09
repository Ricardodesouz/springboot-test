package com.ricardo.estudospringboot.repositories;

import com.ricardo.estudospringboot.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRespository extends JpaRepository<Order, Integer> {
}
