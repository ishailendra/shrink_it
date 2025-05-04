package dev.techsphere.shrinkit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "dev.techsphere.shrinkit")
@EnableCaching
@EnableDiscoveryClient
//@EnableJpaRepositories
public class ShrinkItApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShrinkItApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
