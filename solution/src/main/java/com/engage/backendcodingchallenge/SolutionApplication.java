package com.engage.backendcodingchallenge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SolutionApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(SolutionApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SolutionApplication.class, args);
		logger.debug("--Application Started--");
	}

}
