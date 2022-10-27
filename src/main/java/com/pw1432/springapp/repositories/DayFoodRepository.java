
package com.pw1432.springapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pw1432.springapp.domain.DayFood;
import java.util.List;

public interface DayFoodRepository extends MongoRepository<DayFood, String> {

    DayFood findByFoodIdAndDayId(String foodId, String dayId);

    List<DayFood> findFoodIdsByDayId(String dayId);

    List<DayFood> findDayFoodsByFoodIdAndUserId(String foodId, String userId);

    DayFood findByFoodIdAndDayIdAndUserId(String foodId, String dayId, String userId);

}
