package com.jayson;

import com.jayson.com.jayson.filter.FilterFirstBookmarkRoute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@ComponentScan
@EnableAutoConfiguration
public class RoutingApplication {

	public static void main(String[] args) {
        System.setProperty("spring.config.name", "zuul-routing");
		SpringApplication.run(RoutingApplication.class, args);
	}


    public FilterFirstBookmarkRoute firstFilter() {
        return new FilterFirstBookmarkRoute();
    }
}
