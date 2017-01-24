package com.jayson.com.jayson.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableOAuth2Client
public class oAuthClientConfig {

    @Value("${application.customProps.oauth2.clientId}")
    private String oAuth2ClientId;

    @Value("${security.oauth2.client.clientSecret}")
    private String oAuth2ClientSecret;

    @Value("${security.oauth2.client.accessTokenUri}")
    private String accessTokenUri;

    @Bean
    @Primary
    public ClientCredentialsResourceDetails oauth2RemoteResource() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setClientId(oAuth2ClientId);
        details.setClientSecret(oAuth2ClientSecret);
        details.setAccessTokenUri(accessTokenUri);
        details.setId("1");
        return details;
    }

    @Bean
    @Primary
    public OAuth2ClientContext oAuth2ClientContext() {
        return new DefaultOAuth2ClientContext();
    }

    @Bean
    @Primary
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext context) {
    ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
    details.setClientId(oAuth2ClientId);
    details.setClientSecret(oAuth2ClientSecret);
    details.setAccessTokenUri(accessTokenUri);
    details.setId("1");
    /*

    When using @EnableOAuth2Client spring creates a OAuth2ClientContext for us:

    "The OAuth2ClientContext is placed (for you) in session scope to keep the state for different users separate.
    Without that you would have to manage the equivalent data structure yourself on the server,
    mapping incoming requests to users, and associating each user with a separate instance of the OAuth2ClientContext."
    (http://projects.spring.io/spring-security-oauth/docs/oauth2.html#client-configuration)

    Internally the SessionScope works with a threadlocal to store variables, hence a new thread cannot access those.
    Therefore we can not use @Async

    Solution: create a new OAuth2ClientContext that has no scope.
    *Note: this is only safe when using client_credentials as OAuth grant type!

     */

//        OAuth2RestTemplate restTemplate = new      OAuth2RestTemplate(resourceDetails, oauth2ClientContext);
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(details, context);

        return restTemplate;
    }
}
