package com.ricardo.estudospringboot.repositories;

import com.ricardo.estudospringboot.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
