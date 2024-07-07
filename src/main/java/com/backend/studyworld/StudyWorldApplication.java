package com.backend.studyworld;

import com.backend.studyworld.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class StudyWorldApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepo;

    public static void main(String[] args) {
        SpringApplication.run(StudyWorldApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
