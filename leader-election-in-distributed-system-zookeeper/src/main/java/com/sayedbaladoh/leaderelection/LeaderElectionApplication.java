package com.sayedbaladoh.leaderelection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LeaderElectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeaderElectionApplication.class, args);
    }

}
