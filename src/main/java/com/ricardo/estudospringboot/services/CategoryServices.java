package com.ricardo.estudospringboot.services;

import com.ricardo.estudospringboot.entities.Category;
import com.ricardo.estudospringboot.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServices {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
    public Category findById(Integer id){
        Optional<Category> obj = categoryRepository.findById(id);
        return obj.get();
    }

}
