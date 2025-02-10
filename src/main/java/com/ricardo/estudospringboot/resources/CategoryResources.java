package com.ricardo.estudospringboot.resources;

import com.ricardo.estudospringboot.entities.Category;
import com.ricardo.estudospringboot.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResources {

    @Autowired
    private CategoryServices categoryServices;

    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        return ResponseEntity.ok().body(categoryServices.findAll());
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(categoryServices.findById(id));
    }



}
