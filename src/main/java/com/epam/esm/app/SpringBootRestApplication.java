package com.epam.esm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main spring application class, that contains application entry point.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.epam.esm.controller",
        "com.epam.esm.service.impl",
        "com.epam.esm.config",
        "com.epam.esm.exception"
})
@EntityScan(basePackages = {"com.epam.esm.model.entity"})
@EnableJpaRepositories(basePackages = "com.epam.esm.model.repository")
public class SpringBootRestApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestApplication.class, args);
    }

}
