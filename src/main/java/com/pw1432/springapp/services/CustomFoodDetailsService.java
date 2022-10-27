
package com.pw1432.springapp.services;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pw1432.springapp.domain.Food;
import com.pw1432.springapp.repositories.FoodRepository;

@Service
public class CustomFoodDetailsService {

    @Autowired
    private FoodRepository foodRepository;

    public Food findFoodByName(String name) {
        return foodRepository.findByName(name);
    }

    public List<Food> findAllFoods() {
        return foodRepository.findAll();
    }

    public Optional<Food> findFoodById(String id) {
        return foodRepository.findById(id);
    }

    public void saveFood(Food food) {
        foodRepository.save(food);
    }
}
