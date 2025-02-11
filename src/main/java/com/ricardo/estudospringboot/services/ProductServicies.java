package com.ricardo.estudospringboot.services;

import com.ricardo.estudospringboot.entities.Product;
import com.ricardo.estudospringboot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServicies {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();

    }
    public Product findById(Integer id){
        Optional<Product> obj = productRepository.findById(id);
        return obj.get();
    }


}
