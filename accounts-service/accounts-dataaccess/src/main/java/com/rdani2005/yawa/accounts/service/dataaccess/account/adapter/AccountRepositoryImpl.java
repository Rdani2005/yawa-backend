package com.rdani2005.yawa.accounts.service.dataaccess.account.adapter;

import com.rdani2005.yawa.accounts.service.dataaccess.account.entity.AccountEntity;
import com.rdani2005.yawa.accounts.service.dataaccess.account.mapper.AccountDataAccessDataMapper;
import com.rdani2005.yawa.accounts.service.dataaccess.account.repository.AccountJpaRepository;
import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.accounts.service.domain.ports.output.repository.AccountsRepository;
import com.rdani2005.yawa.domain.valueobject.AccountId;
import com.rdani2005.yawa.domain.valueobject.CustomerId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the AccountsRepository interface for accessing account data.
 */
@Component
public class AccountRepositoryImpl implements AccountsRepository {
    private final AccountJpaRepository accountJpaRepository;
    private final AccountDataAccessDataMapper accountDataAccessDataMapper;

    /**
     * Constructs an AccountRepositoryImpl.
     *
     * @param accountJpaRepository       The JPA repository for account entities.
     * @param accountDataAccessDataMapper The mapper for transforming data between entities and domain objects.
     */
    public AccountRepositoryImpl(
            AccountJpaRepository accountJpaRepository,
            AccountDataAccessDataMapper accountDataAccessDataMapper
    ) {
        this.accountJpaRepository = accountJpaRepository;
        this.accountDataAccessDataMapper = accountDataAccessDataMapper;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Account saveAccount(Account account) {
        return accountDataAccessDataMapper.accountEntityToAccount(
                accountJpaRepository.save(
                        accountDataAccessDataMapper.accountToAccountEntity(
                                account
                        )
                )
        );
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<Account> readAccountByAccountId(AccountId accountId) {
        AccountEntity accountEntity = accountJpaRepository.getById(accountId.getValue());
        if (accountEntity == null) {
            return Optional.empty();
        }

        return Optional.of(accountDataAccessDataMapper.accountEntityToAccount(accountEntity));
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<List<Account>> readAccountsByCustomerId(CustomerId customerId) {
        return Optional.of(
                accountJpaRepository.getAccountEntitiesByCustomerId(customerId.getValue())
                        .stream()
                        .map(accountDataAccessDataMapper::accountEntityToAccount)
                        .collect(Collectors.toList())
        );
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteAccount(Account account) {
        accountJpaRepository.delete(
                accountDataAccessDataMapper.accountToAccountEntity(account)
        );
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<Account> readAccountByAccountNumber(String accountNumber) {
        AccountEntity accountEntity = accountJpaRepository.getAccountEntityByAccountNumber(accountNumber);
        if (accountEntity == null) {
            return Optional.empty();
        }
        return Optional.of(accountDataAccessDataMapper.accountEntityToAccount(accountEntity));
    }
}
