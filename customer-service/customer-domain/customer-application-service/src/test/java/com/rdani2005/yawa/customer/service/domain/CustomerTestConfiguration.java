package com.rdani2005.yawa.customer.service.domain;

import com.rdani2005.yawa.customer.service.domain.ports.output.message.publisher.CustomerCreateMessagePublisher;
import com.rdani2005.yawa.customer.service.domain.ports.output.message.publisher.CustomerDeletedMessagePublisher;
import com.rdani2005.yawa.customer.service.domain.ports.output.repository.CustomerRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.rdani2005.yawa")
public class CustomerTestConfiguration {
    @Bean
    public CustomerDomainService customerDomainService() {
        return new CustomerDomainServiceImpl();
    }

    @Bean
    public CustomerCreateMessagePublisher customerCreateMessagePublisher() {
        return Mockito.mock(CustomerCreateMessagePublisher.class);
    }

    @Bean
    public CustomerDeletedMessagePublisher customerDeletedMessagePublisher() {
        return Mockito.mock(CustomerDeletedMessagePublisher.class);
    }

    @Bean
    public CustomerRepository customerRepository() {
        return Mockito.mock(CustomerRepository.class);
    }
}
