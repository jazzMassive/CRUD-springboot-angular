package com.crudbackend.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.crudbackend.model")
@SpringBootApplication
public class CrudbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudbackendApplication.class, args);
	}

}
