
package com.pw1432.springapp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pw1432.springapp.domain.Day;
import com.pw1432.springapp.domain.DayFood;
import com.pw1432.springapp.domain.Food;
import com.pw1432.springapp.domain.User;
import com.pw1432.springapp.services.CustomUserDetailsService;
import com.pw1432.springapp.services.CustomDayDetailsService;
import com.pw1432.springapp.services.CustomFoodDetailsService;
import com.pw1432.springapp.services.CustomDayFoodDetailsService;

@RestController
public class AuthController {

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private CustomDayDetailsService dayService;

    @Autowired
    private CustomFoodDetailsService foodService;

    @Autowired
    private CustomDayFoodDetailsService dayFoodService;

    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createNewUser(User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signup");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<Day> days = dayService.findDaysByUserId(user.getId());
        modelAndView.addObject("days", days);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.POST)
    public ModelAndView newDay(Day entryDate, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("redirect:/home");
        Day day = new Day();
        day.setEntryDate(entryDate.getEntryDate());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        day.setUserId(user.getId());
        Day dayExists = dayService.findDayByEntryDateAndUserId(day.getEntryDate(), day.getUserId());
        String message = "Day has been entried successfully";
        if (dayExists != null) {
            bindingResult
                    .rejectValue("id", "error.day",
                            "There is already a day entried with the date provided.");
        }
        if (bindingResult.hasErrors()) {
            message = "There is already a day entried with the date provided.";
        } else {
            dayService.saveDay(day);
            modelAndView.addObject("day", new Day());
            // modelAndView.setViewName("home");
        }
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @RequestMapping(value = "/food", method = RequestMethod.GET)
    public ModelAndView food() {
        ModelAndView modelAndView = new ModelAndView();
        List<Food> foods = foodService.findAllFoods();
        modelAndView.addObject("foods", foods);
        modelAndView.setViewName("food");
        return modelAndView;
    }

    @RequestMapping(value = "/food", method = RequestMethod.POST)
    public ModelAndView addFood(Food name, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("redirect:/food");
        Food food = new Food();
        food.setName(name.getName());
        food.setCalories(name.getCalories());
        Food foodExists = foodService.findFoodByName(food.getName());
        String message = "Food has been added to the day successfully.";

        if (foodExists != null) {
            bindingResult
                    .rejectValue("id", "error.food",
                            "There is already a food entried with the date provided.");
        }
        if (bindingResult.hasErrors()) {
            message = "There is already a food entried with details provided.";
        } else {
            foodService.saveFood(food);
            modelAndView.addObject("successMessage", "Food has been entried successfully.");
            modelAndView.addObject("food", new Food());
            // modelAndView.setViewName("food");
        }
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @RequestMapping(value = "/days/{id}", method = RequestMethod.GET)
    public ModelAndView day(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView();
        List<DayFood> dayFoodsByDay = dayFoodService.findFoodIdsByDayId(id);
        List<Food> foods = foodService.findAllFoods();
        List<Food> foodlist = new ArrayList<Food>();
        Optional<Day> date = dayService.findDayById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Integer totalCalories = 0;
        for (DayFood dayFood : dayFoodsByDay) {
            if (dayFood.getUserId().equals(user.getId())) {
                Food food = foodService.findFoodById(dayFood.getFoodId()).get();
                totalCalories += food.getCalories();
                foodlist.add(food);
            }
        }
        modelAndView.addObject("totalCalories", totalCalories);
        modelAndView.addObject("foodlist", foodlist);
        modelAndView.addObject("foods", foods);
        modelAndView.addObject("today", id);
        modelAndView.addObject("date", date.get().getEntryDate());
        modelAndView.setViewName("day");
        return modelAndView;
    }

    @RequestMapping(value = "/days/{id}", method = RequestMethod.POST)
    public ModelAndView addFoodToDay(@PathVariable("id") String id, Food food, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("redirect:/days/{id}");
        DayFood dayFood = new DayFood();
        dayFood.setDayId(id);
        dayFood.setFoodId(food.getId());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        dayFood.setUserId(user.getId());
        String message = "Food has been added to the day successfully.";
        DayFood dayFoodExists = dayFoodService.findDayFoodByFoodIdAndDayIdAndUserId(dayFood.getFoodId(),
                dayFood.getDayId(), dayFood.getUserId());
        if (dayFoodExists != null) {
            bindingResult
                    .rejectValue("id", "error.dayFood",
                            "There is already a food entried with the date provided.");
        }
        if (bindingResult.hasErrors()) {
            message = "There is already a food entried with the date provided.";
        } else {
            dayFoodService.saveDayFood(dayFood);
            modelAndView.addObject("dayFood", new DayFood());
        }
        modelAndView.addObject("message", message);
        return modelAndView;
    }
}
