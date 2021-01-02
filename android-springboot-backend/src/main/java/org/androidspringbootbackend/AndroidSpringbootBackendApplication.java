package org.androidspringbootbackend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class AndroidSpringbootBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AndroidSpringbootBackendApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(Environment environment) {
        return args -> {
            log.info("PORT {}", environment.getProperty("server.port"));
            log.info("URL {}", environment.getProperty("spring.datasource.url"));
            log.info("PASSWORD {}",environment.getProperty("spring.datasource.password"));
            log.info("USERNAME {}",environment.getProperty("spring.datasource.username"));
        };
    }
}
