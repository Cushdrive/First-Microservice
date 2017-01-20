package com.jayson.Config;

import com.jayson.testdatagen.DateTimeGen;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jayson on 1/16/17.
 */
@Configuration
public class UtilConfig {
    @Bean
    public DateTimeGen dateTimeGen() {
        return new DateTimeGen();
    }
}
