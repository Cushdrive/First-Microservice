package com.jayson.bookmarks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;

@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
public class MicroserviceApplication {

	public static void main(String[] args) {
        System.setProperty("spring.config.name", "microservice");
		SpringApplication.run(MicroserviceApplication.class, args);
	}

    @Bean
    CommandLineRunner init(AccountRepository accountRepository,
                           BookmarkRepository bookmarkRepository) {
        return (evt) -> Arrays.asList(
                "jbender,jholler,pwebb,ogierke,rwinch,mfischer,mpollack,jlong".split(","))
                .forEach(
                            a -> {
                                Account account = accountRepository.save(new Account(a,
                                        "password"));
                                bookmarkRepository.save(new Bookmark(account,
                                        "http://bookmark1.com/1/" + a, "A description 1."));
                                bookmarkRepository.save(new Bookmark(account,
                                        "http://bookmark1.com/2/" + a, "A description 2."));
                            }
                );
    }
}
