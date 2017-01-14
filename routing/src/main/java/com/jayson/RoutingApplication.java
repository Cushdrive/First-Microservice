package com.jayson;

import com.jayson.com.jayson.filter.FilterFirstBookmarkRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.support.RibbonRequestCustomizer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonRoutingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@ComponentScan
@EnableAutoConfiguration
public class RoutingApplication {

    @Autowired(required = false)
    private List<RibbonRequestCustomizer> requestCustomizers = Collections.emptyList();

	public static void main(String[] args) {
        System.setProperty("spring.config.name", "zuul-routing");
		SpringApplication.run(RoutingApplication.class, args);
	}

    @Bean
    public RibbonRoutingFilter ribbonRoutingFilter(ProxyRequestHelper helper,
                                                   RibbonCommandFactory<?> ribbonCommandFactory) {
        RibbonRoutingFilter filter = new FilterFirstBookmarkRoute(helper, ribbonCommandFactory, this.requestCustomizers);
        return filter;
    }
}
