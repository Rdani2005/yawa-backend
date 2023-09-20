package com.rdani2005.yawa.customer.service.dataaccess.customer.repository;

import com.rdani2005.yawa.customer.service.dataaccess.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, UUID> {
    CustomerEntity getCustomerEntityByIdentification(String identification);
}
