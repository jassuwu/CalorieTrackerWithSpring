package com.pw1432.springapp.repositories;

import com.pw1432.springapp.domain.Food;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface FoodRepository extends MongoRepository<Food, String> {

    @Query("{ 'name' : ?0 }")
    Food findByName(String name);

    @Query("{ 'id' : ?0 }")
    Optional<Food> findById(String id);
}
