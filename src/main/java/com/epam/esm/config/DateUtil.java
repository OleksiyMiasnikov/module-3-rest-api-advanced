package com.epam.esm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.Instant;

@Configuration
public class DateUtil {
    @Bean
    public static Instant getDate(){
        return Instant.now(Clock.systemUTC());
    }
}
