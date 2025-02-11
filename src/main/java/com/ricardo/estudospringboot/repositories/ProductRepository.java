package com.ricardo.estudospringboot.repositories;

import com.ricardo.estudospringboot.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {
}
