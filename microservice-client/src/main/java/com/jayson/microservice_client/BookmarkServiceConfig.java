package com.jayson.microservice_client;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * Created by jayson on 11/6/16.
 */
public class BookmarkServiceConfig {

    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new RoundRobinRule();
    }

}
