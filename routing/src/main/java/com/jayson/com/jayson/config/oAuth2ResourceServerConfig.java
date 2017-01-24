package com.jayson.com.jayson.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Created by jayson on 1/23/17.
 */
@Configuration
@EnableResourceServer
public class oAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static final String POC_RESOURCE_ID = "gateway";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(POC_RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .anonymous().disable()
                .requestMatchers().antMatchers("/api/**").and()
                .authorizeRequests()
                    .antMatchers("/**").access("#oauth2.hasScope('poc-application')");
    }
}
