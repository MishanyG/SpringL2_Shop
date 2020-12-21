package com.geekbrains.repositories;

import com.geekbrains.entities.Price_history;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PriceRepository extends CrudRepository <Price_history, Long> {
    List <Price_history> findAll();
}
