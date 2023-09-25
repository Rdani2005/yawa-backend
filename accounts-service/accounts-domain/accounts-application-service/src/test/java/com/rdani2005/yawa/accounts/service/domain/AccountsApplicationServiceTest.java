package com.rdani2005.yawa.accounts.service.domain;

import com.rdani2005.yawa.accounts.service.domain.dto.api.create.AccountCreateCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.api.create.AccountCreateResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.api.delete.AccountDeleteCommand;
import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.accounts.service.domain.entity.Customer;
import com.rdani2005.yawa.accounts.service.domain.exception.AccountNotFoundException;
import com.rdani2005.yawa.accounts.service.domain.exception.AccountsDomainException;
import com.rdani2005.yawa.accounts.service.domain.mapper.AccountsServiceDataMapper;
import com.rdani2005.yawa.accounts.service.domain.ports.input.service.AccountsApplicationService;
import com.rdani2005.yawa.accounts.service.domain.ports.output.repository.AccountsRepository;
import com.rdani2005.yawa.accounts.service.domain.ports.output.repository.CustomerRepository;
import com.rdani2005.yawa.domain.valueobject.AccountId;
import com.rdani2005.yawa.domain.valueobject.CustomerId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = AccountsTestConfiguration.class)
public class AccountsApplicationServiceTest {
    @Autowired
    private AccountsApplicationService accountsApplicationService;
    @Autowired
    private AccountsServiceDataMapper accountsServiceDataMapper;
    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private CustomerRepository customerRepository;


    private AccountCreateCommand accountCreateCommand;
    private AccountCreateCommand wrongAccountCreateCommand;
    private AccountDeleteCommand accountDeleteCommand;

    private final CustomerId CUSTOMER_ID = new CustomerId(
            UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41")
    );

    private final CustomerId WRONG_CUSTOMER_ID = new CustomerId(
            UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb42")
    );

    private final AccountId ACCOUNT_ID = new AccountId(
            UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb43")
    );

    private final AccountId WRONG_ACCOUNT_ID = new AccountId(
            UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb44")
    );

    @BeforeAll
    public void init() {
        accountCreateCommand = AccountCreateCommand
                .builder()
                .accountNumber("1111-1111-1111-1111")
                .customerId(CUSTOMER_ID.getValue())
                .initialAmount(new BigDecimal("100.00"))
                .build();

        wrongAccountCreateCommand = AccountCreateCommand
                .builder()
                .accountNumber("2222-2222-2222-2222")
                .customerId(CUSTOMER_ID.getValue())
                .initialAmount(new BigDecimal("-0"))
                .build();

        Customer customer =
                Customer
                        .builder()
                        .name("Danny Ricardo")
                        .lastName("Sequeira Campos")
                        .identification("1-1935-0628")
                        .build();

        customer.setId(CUSTOMER_ID);

        accountDeleteCommand = AccountDeleteCommand.builder().accountId(ACCOUNT_ID.getValue()).build();
        Account account = accountsServiceDataMapper.accountCreateCommandToAccount(accountCreateCommand);
        account.setId(ACCOUNT_ID);
        when(accountsRepository.readAccountByAccountId(ACCOUNT_ID)).thenReturn(Optional.of(account));
        when(accountsRepository.saveAccount(any(Account.class))).thenReturn(account);
        when(customerRepository.getCustomerByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(customer));
    }

    @Test
    public void testCreateAccount() {
        AccountCreateResponse accountCreateResponse = accountsApplicationService.createAccount(accountCreateCommand);
        assertEquals("Account was successfully created.", accountCreateResponse.getMessage());
    }

    @Test
    public void testCreateAccountWithWrongInitialAmount() {
        AccountsDomainException accountsDomainException = assertThrows(
            AccountsDomainException.class,
                () -> accountsApplicationService.createAccount(wrongAccountCreateCommand)
        );
        assertEquals("Account initial amount must be greater than 0.", accountsDomainException.getMessage());
    }

    @Test
    public void testDeleteAccountWithWrongId() {
        AccountNotFoundException accountNotFoundException = assertThrows(
                AccountNotFoundException.class,
                () -> accountsApplicationService.deleteAccount(AccountDeleteCommand.builder().accountId(WRONG_ACCOUNT_ID.getValue()).build())
        );

        assertEquals("Account with id: " + WRONG_ACCOUNT_ID.getValue() + " was not found.", accountNotFoundException.getMessage());
    }
}
