package com.jayson;

import com.jayson.microservice_client.Bookmark;
import com.jayson.microservice_client.BookmarkServiceConfig;
import com.netflix.discovery.converters.Auto;
import com.netflix.loadbalancer.*;
import com.netflix.niws.loadbalancer.DefaultNIWSServerListFilter;
import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RibbonClient(name = "bookmark-service", configuration = BookmarkServiceConfig.class)
@EnableDiscoveryClient
@RestController
@EnableAutoConfiguration
public class MicroserviceClientApplication {

	private static final Logger log = LoggerFactory.getLogger(MicroserviceClientApplication.class);

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "microservice-client");
		SpringApplication.run(MicroserviceClientApplication.class, args);
	}

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/myfirstbookmark")
    public String getmine(@RequestParam(value = "username") String username) {

        Bookmark bm;

        bm = this.restTemplate.getForObject(
                String.format("http://bookmark-service/%s/bookmarks/1",username),Bookmark.class
        );

        return bm.toString();
    }
}
