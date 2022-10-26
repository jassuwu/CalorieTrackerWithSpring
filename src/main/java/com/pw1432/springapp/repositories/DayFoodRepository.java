
package com.pw1432.springapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pw1432.springapp.domain.DayFood;

public interface DayFoodRepository extends MongoRepository<DayFood, String> {

    DayFood findByFoodIdAndDayId(String foodId, String dayId);

}
