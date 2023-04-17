package com.epam.esm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Configuration
public class DateUtil {
    private static final String PATTERN_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    @Bean
    public static String getDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());
        return formatter.format(Instant.now(Clock.systemUTC()));
    }
}
