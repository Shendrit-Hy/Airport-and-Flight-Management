package com.mbi_re.airport_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan(basePackages = {"com.mbi_re.airport_management.controller",
//		"com.mbi_re.airport_management.service",
//		"com.mbi_re.airport_management.repository",
//		"com.mbi_re.airport_management.model",
//		"com.mbi_re.airport_management.config",
//		"com.mbi_re.airport_management.dto"
//	})
@SpringBootApplication
public class AirportManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirportManagementApplication.class, args);
	}

}
