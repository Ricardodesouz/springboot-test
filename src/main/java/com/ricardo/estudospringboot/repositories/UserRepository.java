package com.ricardo.estudospringboot.repositories;

import com.ricardo.estudospringboot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
}
