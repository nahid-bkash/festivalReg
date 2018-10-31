package com.bkash.frestivalreg.configuration;



import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.bkash.frestivalreg.domain"})
@EnableJpaRepositories(basePackages = {"com.bkash.frestivalreg.repository"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}