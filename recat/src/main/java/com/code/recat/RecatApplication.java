package com.code.recat;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties()
public class RecatApplication {

	public static void main(String[] args) {

		SpringApplication.run(RecatApplication.class, args);


	}

}
