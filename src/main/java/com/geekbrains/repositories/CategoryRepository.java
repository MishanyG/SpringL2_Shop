package com.geekbrains.repositories;

import com.geekbrains.entities.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    List<Category> findAll();
}
