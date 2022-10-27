
package com.pw1432.springapp.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pw1432.springapp.domain.Day;

public interface DayRepository extends MongoRepository<Day, String> {

    Day findByEntryDate(String entryDate);

    Optional<Day> findById(String id);
}
