package com.engage.backendcodingchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Expenses application.
 * @author patriciaespada
 *
 */
@SpringBootApplication
public class ExpensesApplication {
	
	/**
	 * Main method to start the Expenses application.
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ExpensesApplication.class, args);
	}
	
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
