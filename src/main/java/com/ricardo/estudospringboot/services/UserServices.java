package com.ricardo.estudospringboot.services;

import com.ricardo.estudospringboot.repositories.UserRepository;
import com.ricardo.estudospringboot.services.exceptions.DataBaseException;
import com.ricardo.estudospringboot.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        return user.orElseThrow(() -> new ResourceNotFoundException(id));

    }
    public User insert (User user){
        return userRepository.save(user);
    }
    public void delete(Integer id){
        try {
            userRepository.deleteById(id);
        }catch(DataIntegrityViolationException error ){
            throw new DataBaseException(error.getMessage());
        }
    }

    public User update(Integer id, User user){
        User entity = userRepository.getReferenceById(id);
        updateData(entity,user);
        return userRepository.save(entity);
    }
    private void  updateData(User oldUser, User newUser){
        oldUser.setName(newUser.getName());
        oldUser.setEmail(newUser.getEmail());
    }

}
