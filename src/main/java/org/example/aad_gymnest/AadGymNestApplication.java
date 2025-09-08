package org.example.aad_gymnest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AadGymNestApplication {

    public static void main(String[] args) {
        SpringApplication.run(AadGymNestApplication.class, args);
    }

}
