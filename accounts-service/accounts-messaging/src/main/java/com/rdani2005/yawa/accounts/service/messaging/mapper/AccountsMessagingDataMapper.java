package com.rdani2005.yawa.accounts.service.messaging.mapper;

import com.rdani2005.yawa.accounts.service.domain.dto.messages.customer.CustomerModel;
import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.domain.valueobject.AccountId;
import com.rdani2005.yawa.domain.valueobject.CustomerId;
import com.rdani2005.yawa.domain.valueobject.Money;
import com.rdani2005.yawa.kafka.avro.model.AccountAvroModel;

import com.rdani2005.yawa.kafka.avro.model.CustomerAvroModel;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import static com.rdani2005.yawa.accounts.service.domain.entity.Account.UTC;
/**
 * Mapper class to convert between {@link Account} and {@link AccountAvroModel} for Kafka messages.
 *
 */
@Component
public class AccountsMessagingDataMapper {

    /**
     * Convert an {@link Account} object to an {@link AccountAvroModel} object.
     *
     * @param account The {@link Account} object to convert.
     * @return The converted {@link AccountAvroModel} object.
     */
    public AccountAvroModel accountToAccountAvroModel(Account account) {
        return AccountAvroModel
                .newBuilder()
                .setId(account.getId().getValue().toString())
                .setCustomerId(account.getCustomerId().getValue().toString())
                .setAccountNumber(account.getAccountNumber())
                .setInitialAmount(account.getInitialAmount().getAmount())
                .setActualAmount(account.getActualAmount().getAmount())
                .setCreatedAt(account.getCreatedAt().toInstant())
                .build();
    }

    /**
     * Convert an {@link AccountAvroModel} object to an {@link Account} object.
     *
     * @param accountAvroModel The {@link AccountAvroModel} object to convert.
     * @return The converted {@link Account} object.
     */
    public Account accountAvroModelToAccount(AccountAvroModel accountAvroModel) {
        return Account.Builder
                .builder()
                .id(new AccountId(UUID.fromString(accountAvroModel.getId())))
                .customerId(new CustomerId(UUID.fromString(accountAvroModel.getCustomerId())))
                .accountNumber(accountAvroModel.getAccountNumber())
                .initialAmount(new Money(accountAvroModel.getInitialAmount()))
                .actualAmount(new Money(accountAvroModel.getActualAmount()))
                .createdAt(ZonedDateTime.ofInstant(accountAvroModel.getCreatedAt(), ZoneId.of(UTC)))
                .build();
    }

    public CustomerModel customerAvroModeltoCustomerModel(CustomerAvroModel customerAvroModel) {
        return CustomerModel
                .builder()
                .customerId(UUID.fromString(customerAvroModel.getId()))
                .name(customerAvroModel.getName())
                .lastName(customerAvroModel.getLastName())
                .identification(customerAvroModel.getIdentification())
                .birthDay(ZonedDateTime.ofInstant(customerAvroModel.getBirthDay(), ZoneId.of(UTC)))
                .createdAt(ZonedDateTime.ofInstant(customerAvroModel.getCreatedAt(), ZoneId.of(UTC)))
                .build();
    }
}
