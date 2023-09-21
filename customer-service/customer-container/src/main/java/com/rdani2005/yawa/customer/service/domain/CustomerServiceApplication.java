package com.rdani2005.yawa.customer.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "com.rdani2005.yawa.customer.service.dataaccess" })
@EntityScan(basePackages = { "com.rdani2005.yawa.customer.service.dataaccess" })
@SpringBootApplication(scanBasePackages = "com.rdani2005.yawa")
public class CustomerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
}
