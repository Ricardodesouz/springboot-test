package com.ricardo.estudospringboot.resources;

import com.ricardo.estudospringboot.services.UserServices;
import com.ricardo.estudospringboot.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public ResponseEntity<User> Insert(@RequestBody User user){
        return ResponseEntity.ok().body(userServices.insert(user));
    }
}
