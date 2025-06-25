package com.salh;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableAutoConfiguration(exclude = { SessionAutoConfiguration.class })
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EntityScan(basePackages = { "com.salh" })
@EnableJpaRepositories(basePackages = {"com.salh" }, repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)

public class ConstructionExpensesManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConstructionExpensesManagerApplication.class, args);
	}
	
	@Bean
	public AuditorAware<String> auditorAware() {
		return () -> Optional.of("default");
	}

}
