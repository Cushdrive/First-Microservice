package com.jayson.com.jayson.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * Created by jayson on 1/16/17.
 */
@Configuration
@EnableOAuth2Sso
public class OAuthConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .logout().and().antMatcher("/**").authorizeRequests()
                    .antMatchers("/login","/auth/**").permitAll()
                    .anyRequest().authenticated().and().csrf().disable();
    }
}
