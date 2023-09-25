package com.rdani2005.yawa.accounts.service.dataaccess.account.repository;

import com.rdani2005.yawa.accounts.service.dataaccess.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA repository interface for accessing AccountEntity data.
 */
@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, UUID> {

    /**
     * Retrieves an AccountEntity by its account number.
     *
     * @param accountNumber The account number to search for.
     * @return An AccountEntity if found, or null if not found.
     */
    AccountEntity getAccountEntityByAccountNumber(String accountNumber);

    /**
     * Retrieves a list of AccountEntities associated with a customer ID.
     *
     * @param customerId The customer ID to search for.
     * @return A list of AccountEntities associated with the customer, or an empty list if none are found.
     */
    List<AccountEntity> getAccountEntitiesByCustomerId(UUID customerId);
}
