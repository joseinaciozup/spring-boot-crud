package com.spring.springbootcrud.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCircuitBreaker
@EnableHystrixDashboard
@EntityScan(basePackages = "com.spring.springbootcrud.domain.entity")
@EnableJpaRepositories(basePackages = "com.spring.springbootcrud.domain.repository")
@SpringBootApplication(scanBasePackages = "com.spring.springbootcrud")
public class SpringBootCrudApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootCrudApplication.class, args);
  }
}
