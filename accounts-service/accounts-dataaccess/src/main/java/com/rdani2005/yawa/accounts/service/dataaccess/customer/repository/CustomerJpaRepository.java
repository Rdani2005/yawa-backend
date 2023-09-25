package com.rdani2005.yawa.accounts.service.dataaccess.customer.repository;

import com.rdani2005.yawa.accounts.service.dataaccess.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for managing customer entities.
 */
@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, UUID> {
}
