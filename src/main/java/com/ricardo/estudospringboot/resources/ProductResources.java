package com.ricardo.estudospringboot.resources;

import com.ricardo.estudospringboot.entities.Product;
import com.ricardo.estudospringboot.services.ProductServicies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value ="/products")
public class ProductResources {


    @Autowired
    private ProductServicies productServicies;

    @GetMapping
    public ResponseEntity<List<Product>> findAll(){
        return ResponseEntity.ok().body(productServicies.findAll());
    }
    @GetMapping(value ="/{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(productServicies.findById(id));
    }
}
