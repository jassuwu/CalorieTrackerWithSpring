package com.pw1432.springapp.domain;

public class DayFood {
    private String foodId;
    private String dayId;
    private String userId;

    public DayFood() {
    }

    public DayFood(String foodId, String dayId, String userId) {
        this.foodId = foodId;
        this.dayId = dayId;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "DayFood [dayId=" + dayId + ", foodId=" + foodId + ", userId=" + userId + "]";
    }
}
