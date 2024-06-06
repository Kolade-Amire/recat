package com.code.recat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class RecatApplication {

//	@Autowired
//	private ClassPathCheck classPathCheck;

	public static void main(String[] args) {

		SpringApplication.run(RecatApplication.class, args);



	}

//	@Override
//	public void run(String... args) throws Exception {
//		String fileName = "recat/src/test/resources/user/user_single.json";
//		classPathCheck.checkFileInClasspath(fileName);
//	}
}
