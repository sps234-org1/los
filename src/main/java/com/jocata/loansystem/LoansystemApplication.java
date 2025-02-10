package com.jocata.loansystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.jocata.loansystem.dao")
@ComponentScan(basePackages = "com.jocata.loansystem")
@EntityScan(basePackages = "com.jocata.loansystem.entity")
public class LoansystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansystemApplication.class, args);
	}
}
