package com.bkash.festivalreg.configuration;



import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.bkash.festivalreg.domain"})
@EnableJpaRepositories(basePackages = {"com.bkash.festivalreg.repository"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}