package com.rdani2005.yawa.accounts.service.domain.entity;

import com.rdani2005.yawa.accounts.service.domain.exception.AccountDomainException;
import com.rdani2005.yawa.domain.entity.AggregateRoot;
import com.rdani2005.yawa.domain.valueobject.AccountId;
import com.rdani2005.yawa.domain.valueobject.CustomerId;
import com.rdani2005.yawa.domain.valueobject.Money;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Account extends AggregateRoot<AccountId> {
    private final CustomerId customerId;
    private final Money initialAmount;
    private Money currentAmount;
    private final String acocuntNumber;
    private final ZonedDateTime createdAt;
    public static final String FAILURE_MESSAGE_DELIMITER = ",";

    private Account(Builder builder) {
        customerId = builder.customerId;
        initialAmount = builder.initialAmount;
        currentAmount = builder.currentAmount;
        acocuntNumber = builder.acocuntNumber;
        createdAt = builder.createdAt;
        super.setId(builder.id);
    }

    public void initializeAccount(Money initialAmount) {
        if (initialAmount.isGreaterThanZero()) {
            throw new AccountDomainException("Account cannot be initialized with a < 0 value");
        }
        setId(new AccountId(UUID.randomUUID()));
    }

    public void makeTransaction(Money transactionAmount) {
        if (transactionAmount.multiply(-1).isGreaterThan(this.currentAmount)) {
            throw new AccountDomainException("Account cannot be updated with the given amount.");
        }

        currentAmount.substract(transactionAmount);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private void validateDeleteAccount() {
        if (this.currentAmount.isGreaterThanZero()) {
            throw new AccountDomainException("Account can't be removed because it has money on it");
        }
    }
    /**
     * {@code Account} builder static inner class.
     */
    public static final class Builder {
        private CustomerId customerId;
        private Money initialAmount;
        private Money currentAmount;
        private String acocuntNumber;
        private ZonedDateTime createdAt;
        private AccountId id;

        private Builder() {
        }



        /**
         * Sets the {@code customerId} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code customerId} to set
         * @return a reference to this Builder
         */
        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        /**
         * Sets the {@code initialAmount} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code initialAmount} to set
         * @return a reference to this Builder
         */
        public Builder initialAmount(Money val) {
            initialAmount = val;
            return this;
        }

        /**
         * Sets the {@code currentAmount} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code currentAmount} to set
         * @return a reference to this Builder
         */
        public Builder currentAmount(Money val) {
            currentAmount = val;
            return this;
        }

        /**
         * Sets the {@code acocuntNumber} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code acocuntNumber} to set
         * @return a reference to this Builder
         */
        public Builder acocuntNumber(String val) {
            acocuntNumber = val;
            return this;
        }

        /**
         * Sets the {@code createdAt} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code createdAt} to set
         * @return a reference to this Builder
         */
        public Builder createdAt(ZonedDateTime val) {
            createdAt = val;
            return this;
        }

        /**
         * Sets the {@code id} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code id} to set
         * @return a reference to this Builder
         */
        public Builder id(AccountId val) {
            id = val;
            return this;
        }

        /**
         * Returns a {@code Account} built from the parameters previously set.
         *
         * @return a {@code Account} built with parameters of this {@code Account.Builder}
         */
        public Account build() {
            return new Account(this);
        }
    }
}
