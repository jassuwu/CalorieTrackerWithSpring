package com.pw1432.springapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pw1432.springapp.domain.Role;

public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByRole(String role);

}
