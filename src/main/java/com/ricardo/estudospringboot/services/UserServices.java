package com.ricardo.estudospringboot.services;

import com.ricardo.estudospringboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ricardo.estudospringboot.entities.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }
    public User findById(Integer id){
        Optional<User> user = userRepository.findById(id);
        return user.get();

    }
    public User insert (User user){
        return userRepository.save(user);
    }

}
