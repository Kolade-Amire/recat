package com.code.recat;


import com.code.recat.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class RecatApplication {

	public static void main(String[] args) {

		SpringApplication.run(RecatApplication.class, args);


	}

}
