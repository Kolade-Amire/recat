package com.code.recat;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableConfigurationProperties()
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class RecatApplication {

	public static void main(String[] args) {

		SpringApplication.run(RecatApplication.class, args);


	}

}
