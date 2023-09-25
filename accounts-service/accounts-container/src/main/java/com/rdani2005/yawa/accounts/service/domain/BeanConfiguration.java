package com.rdani2005.yawa.accounts.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public AccountsDomainService accountsDomainService() {
        return new AccountsDomainServiceImpl();
    }
}
