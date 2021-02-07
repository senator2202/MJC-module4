package com.epam.esm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.epam.esm.controller",
        "com.epam.esm.model.dao.impl",
        "com.epam.esm.service.impl",
        "com.epam.esm.config"
})
@EntityScan(basePackages = {"com.epam.esm.model.entity"})
public class SpringBootRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestApplication.class, args);
    }

}
