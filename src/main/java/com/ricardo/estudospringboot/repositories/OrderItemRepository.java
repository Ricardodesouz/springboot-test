package com.ricardo.estudospringboot.repositories;

import com.ricardo.estudospringboot.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
}
