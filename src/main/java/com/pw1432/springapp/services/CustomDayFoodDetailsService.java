package com.pw1432.springapp.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pw1432.springapp.domain.DayFood;
import com.pw1432.springapp.domain.Food;
import com.pw1432.springapp.repositories.DayFoodRepository;

@Service
public class CustomDayFoodDetailsService {

    @Autowired
    private DayFoodRepository dayFoodRepository;

    public DayFood findDayFoodByFoodIdAndDayId(String foodId, String dayId) {
        return dayFoodRepository.findByFoodIdAndDayId(foodId, dayId);
    }

    public List<DayFood> findFoodIdsByDayId(String dayId) {
        return dayFoodRepository.findFoodIdsByDayId(dayId);
    }

    public List<DayFood> findDayFoodsByFoodIdAndUserId(String foodId, String userId) {
        return dayFoodRepository.findDayFoodsByFoodIdAndUserId(foodId, userId);
    }

    public void saveDayFood(DayFood dayFood) {
        dayFoodRepository.save(dayFood);
    }

    public List<Food> findFoodIdByDayId(String id) {
        return null;
    }

    public List<Food> findFoodsIdByDayId(String id) {
        return null;
    }
}
