package com.jayson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigApplication {

	public static void main(String[] args) {
        System.setProperty("spring.config.name", "config-server");
        SpringApplication.run(ConfigApplication.class, args);
	}
}
