package com.crudbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EntityScan("com.crudbackend.model")
@SpringBootApplication
public class CrudbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudbackendApplication.class, args);
	}
}
