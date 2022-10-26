
package com.pw1432.springapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pw1432.springapp.domain.Day;
import com.pw1432.springapp.domain.Food;
import com.pw1432.springapp.domain.User;
import com.pw1432.springapp.services.CustomUserDetailsService;
import com.pw1432.springapp.services.CustomDayDetailsService;
import com.pw1432.springapp.services.CustomFoodDetailsService;

@RestController
public class AuthController {

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private CustomDayDetailsService dayService;

    @Autowired
    private CustomFoodDetailsService foodService;

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

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName", "Welcome " + user.getFullname());
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }

    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        List<Day> days = dayService.findAllDays();
        modelAndView.addObject("days", days);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.POST)
    public ModelAndView newDay(Day entryDate, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Day day = new Day();
        day.setEntryDate(entryDate.getEntryDate());
        Day dayExists = dayService.findDayByEntryDate(day.getEntryDate());
        if (dayExists != null) {
            bindingResult
                    .rejectValue("day", "error.day",
                            "There is already a day entried with the date provided.");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("home");
        } else {
            dayService.saveDay(day);
            modelAndView.addObject("successMessage", "Day has been entried successfully.");
            modelAndView.addObject("day", new Day());
            modelAndView.setViewName("home");
        }
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
        ModelAndView modelAndView = new ModelAndView();
        Food food = new Food();
        food.setName(name.getName());
        food.setCalories(name.getCalories());
        Food foodExists = foodService.findFoodByName(food.getName());
        if (foodExists != null) {
            bindingResult
                    .rejectValue("food", "error.food",
                            "There is already a food entried with the date provided.");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("food");
        } else {
            foodService.saveFood(food);
            modelAndView.addObject("successMessage", "Food has been entried successfully.");
            modelAndView.addObject("food", new Food());
            modelAndView.setViewName("food");
        }
        return modelAndView;
    }
}
