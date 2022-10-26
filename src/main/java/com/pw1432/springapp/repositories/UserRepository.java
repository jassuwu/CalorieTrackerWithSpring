
package com.pw1432.springapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pw1432.springapp.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

}
