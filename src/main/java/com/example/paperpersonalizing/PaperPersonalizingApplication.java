package com.example.paperpersonalizing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.paperpersonalizing.config"})
public class PaperPersonalizingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaperPersonalizingApplication.class, args);
    }

}
