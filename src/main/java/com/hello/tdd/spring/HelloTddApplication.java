package com.hello.tdd.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HelloTddApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloTddApplication.class, args);
    }


}