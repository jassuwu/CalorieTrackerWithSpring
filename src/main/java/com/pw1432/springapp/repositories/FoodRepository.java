package com.pw1432.springapp.repositories;

import com.pw1432.springapp.domain.Food;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodRepository extends MongoRepository<Food, String> {
    Food findByName(String name);

    Optional<Food> findById(String id);
}
