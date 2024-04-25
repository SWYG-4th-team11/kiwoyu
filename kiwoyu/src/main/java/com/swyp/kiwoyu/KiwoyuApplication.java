package com.swyp.kiwoyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages={"com.swyp.kiwoyu"})
public class KiwoyuApplication {

	public static void main(String[] args) {
		SpringApplication.run(KiwoyuApplication.class, args);
	}

}
