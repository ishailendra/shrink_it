package com.shail.shrinkit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.shail.shrinkit")
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableCaching
public class ShrinkItApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShrinkItApplication.class, args);
	}

}
