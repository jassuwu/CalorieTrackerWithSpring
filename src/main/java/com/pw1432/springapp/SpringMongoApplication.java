package com.pw1432.springapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.pw1432.springapp.domain.Role;
import com.pw1432.springapp.repositories.RoleRepository;

@SpringBootApplication
public class SpringMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMongoApplication.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository) {

        return args -> {

            Role adminRole = roleRepository.findByRole("ADMIN");
            if (adminRole == null) {
                Role newAdminRole = new Role();
                newAdminRole.setRole("ADMIN");
                roleRepository.save(newAdminRole);
            }

            // Role userRole = roleRepository.findByRole("USER");
            // if (userRole == null) {
            // Role newUserRole = new Role();
            // newUserRole.setRole("USER");
            // roleRepository.save(newUserRole);
            // }
        };

    }

}
