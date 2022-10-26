package com.pw1432.springapp.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class DayFood {
    @DBRef
    private String foodId;
    @DBRef
    private String dayId;

    public DayFood() {
    }

    public DayFood(String foodId, String dayId) {
        this.foodId = foodId;
        this.dayId = dayId;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }

    @Override
    public String toString() {
        return "DayFood [dayId=" + dayId + ", foodId=" + foodId + "]";
    }
}
