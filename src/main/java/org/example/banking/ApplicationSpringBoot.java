package org.example.banking;

import org.example.banking.application.ApplicationFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApplicationSpringBoot {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationSpringBoot.class, args);
    }

    @Bean
    ApplicationFacade applicationFacade() {
        return ApplicationFacade.inMemoryApplication();
    }
}
