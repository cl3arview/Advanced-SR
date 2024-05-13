package org.advancedsr;

import org.advancedsr.entities.Role;
import org.advancedsr.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository) {
        return args -> {
            Role adminRole = Role.builder()
                    .roleName("User")
                    .permissions("Default Permissions.")
                    .build();
            roleRepository.save(adminRole);
        };
    }
}