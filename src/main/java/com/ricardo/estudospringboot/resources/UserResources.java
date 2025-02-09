package com.ricardo.estudospringboot.resources;

import com.ricardo.estudospringboot.services.UserServices;
import com.ricardo.estudospringboot.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResources {

    @Autowired
    private UserServices userServices;

    @GetMapping
    public ResponseEntity<List<User>>findAll(){
        return ResponseEntity.ok().body(userServices.findAll());

    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(userServices.findById(id));
    }
}
