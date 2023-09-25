package com.rdani2005.yawa.accounts.service.domain;

import com.rdani2005.yawa.accounts.service.domain.ports.output.message.publisher.AccountCreatedEventPublisher;
import com.rdani2005.yawa.accounts.service.domain.ports.output.message.publisher.AccountDeletedEventPublisher;
import com.rdani2005.yawa.accounts.service.domain.ports.output.repository.AccountsRepository;
import com.rdani2005.yawa.accounts.service.domain.ports.output.repository.CustomerRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.rdani2005.yawa")
public class AccountsTestConfiguration {

    @Bean
    public AccountsDomainService accountsDomainService() {
        return new AccountsDomainServiceImpl();
    }

    @Bean
    public AccountCreatedEventPublisher accountCreatedEventPublisher() {
        return Mockito.mock(AccountCreatedEventPublisher.class);
    }

    @Bean
    public AccountDeletedEventPublisher accountDeletedEventPublisher() {
        return Mockito.mock(AccountDeletedEventPublisher.class);
    }

    @Bean
    public AccountsRepository accountsRepository() {
        return Mockito.mock(AccountsRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository() {
        return Mockito.mock(CustomerRepository.class);
    }
}
