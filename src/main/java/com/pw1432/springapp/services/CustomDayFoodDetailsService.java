
package com.pw1432.springapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pw1432.springapp.domain.DayFood;
import com.pw1432.springapp.repositories.DayFoodRepository;

@Service
public class CustomDayFoodDetailsService {

    @Autowired
    private DayFoodRepository dayFoodRepository;

    public DayFood findDayFoodByFoodIdAndDayId(String foodId, String dayId) {
        return dayFoodRepository.findByFoodIdAndDayId(foodId, dayId);
    }

    public void saveDayFood(DayFood dayFood) {
        dayFoodRepository.save(dayFood);
    }
}
