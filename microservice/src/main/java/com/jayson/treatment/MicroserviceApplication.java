package com.jayson.treatment;

import com.jayson.testdatagen.DateTimeGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.util.Arrays;

@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
@ComponentScan("com.jayson")
@EnableResourceServer
public class MicroserviceApplication {

    @Autowired
    private DateTimeGen dateTimeGenerator;

	public static void main(String[] args) {
        System.setProperty("spring.config.name", "microservice");
		SpringApplication.run(MicroserviceApplication.class, args);
	}

    @Bean
    CommandLineRunner init(PatientRepository patientRepository,
                           TreatmentRepository treatmentRepository) {
        return (evt) -> Arrays.asList(
                "jbender,jholler,pwebb,ogierke,rwinch,mfischer,mpollack,jlong".split(","))
                .forEach(
                            a -> {
                                Patient patient = patientRepository.save(new Patient(a,
                                        "password"));
                                //patient, alarm, modality, treatment date time
                                treatmentRepository.save(new Treatment(patient,
                                        "No severe alarms generated for " + a, "APD", dateTimeGenerator.getRandomDateTimeFromLast24()));
                                treatmentRepository.save(new Treatment(patient,
                                        "No severe alarms generated for " + a, "HHD", dateTimeGenerator.getRandomDateTimeFromLast24()));
                            }
                );
    }
}
