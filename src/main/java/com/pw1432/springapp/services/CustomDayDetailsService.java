
package com.pw1432.springapp.services;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pw1432.springapp.domain.Day;
import com.pw1432.springapp.repositories.DayRepository;

@Service
public class CustomDayDetailsService {

    @Autowired
    private DayRepository dayRepository;

    public Day findDayByEntryDate(String entryDate) {
        return dayRepository.findByEntryDate(entryDate);
    }

    public Optional<Day> findDayById(String id) {
        return dayRepository.findById(id);
    }

    public List<Day> findAllDays() {
        return dayRepository.findAll();
    }

    public void saveDay(Day day) {
        dayRepository.save(day);
    }
}
