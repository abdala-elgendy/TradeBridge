package com.global.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.retry.annotation.EnableRetry;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableScheduling
@EnableRetry
@Slf4j
public class TradeBridgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeBridgeApplication.class, args);
    }

    @Bean
    ApplicationRunner logOnStartup() {
        return args -> {
            log.info("Trade Bridge application started successfully.");
            log.debug("Logging initialized. Check files under 'logs/' directory.");
        };
    }
}
