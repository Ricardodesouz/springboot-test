package com.ricardo.estudospringboot.resources;

import com.ricardo.estudospringboot.services.UserServices;
import com.ricardo.estudospringboot.entities.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
        user = userServices.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }
    @DeleteMapping(value = {"/{id}"})
     public ResponseEntity<Void> deleteById(@PathVariable Integer id){
        userServices.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Integer id, @RequestBody User user){
        return ResponseEntity.ok().body(userServices.update(id,user));
    }
}
