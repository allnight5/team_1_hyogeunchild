package com.sparta.team_1_hyogeunchild;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Team1HyogeunchildApplication {

    public static void main(String[] args) {
        SpringApplication.run(Team1HyogeunchildApplication.class, args);
    }

}
