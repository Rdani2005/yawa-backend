package com.rdani2005.yawa.accounts.service.dataaccess.account.mapper;

import com.rdani2005.yawa.accounts.service.dataaccess.account.entity.AccountEntity;
import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.domain.valueobject.AccountId;
import com.rdani2005.yawa.domain.valueobject.CustomerId;
import com.rdani2005.yawa.domain.valueobject.Money;

import org.springframework.stereotype.Component;

/**
 * Mapper class for mapping between AccountEntity and Account objects.
 */
@Component
public class AccountDataAccessDataMapper {
    /**
     * Maps an AccountEntity to an Account object.
     *
     * @param accountEntity The AccountEntity to map.
     * @return An Account object.
     */
    public Account accountEntityToAccount(AccountEntity accountEntity) {
        return Account
                .Builder
                .builder()
                .id(new AccountId(accountEntity.getId()))
                .accountNumber(accountEntity.getAccountNumber())
                .customerId(new CustomerId(accountEntity.getCustomerId()))
                .initialAmount(new Money(accountEntity.getInitialAmount()))
                .actualAmount(new Money(accountEntity.getActualAmount()))
                .createdAt(accountEntity.getCreatedAt())
                .build();
    }

    /**
     * Maps an Account object to an AccountEntity.
     *
     * @param account The Account object to map.
     * @return An AccountEntity.
     */
    public AccountEntity accountToAccountEntity(Account account) {
        return AccountEntity
                .builder()
                .id(account.getId().getValue())
                .customerId(account.getCustomerId().getValue())
                .accountNumber(account.getAccountNumber())
                .initialAmount(account.getInitialAmount().getAmount())
                .actualAmount(account.getActualAmount().getAmount())
                .createdAt(account.getCreatedAt())
                .build();
    }
}
