package com.rdani2005.yawa.accounts.service.domain.entity;

import com.rdani2005.yawa.accounts.service.domain.exception.AccountsDomainException;
import com.rdani2005.yawa.domain.entity.AggregateRoot;
import com.rdani2005.yawa.domain.valueobject.AccountId;
import com.rdani2005.yawa.domain.valueobject.CustomerId;
import com.rdani2005.yawa.domain.valueobject.Money;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Represents an account entity in the system. Accounts are associated with customers and hold a balance.
 * This class is an aggregate root.
 */
public class Account extends AggregateRoot<AccountId> {
    private final CustomerId customerId;
    private final String accountNumber;
    private final Money initialAmount;
    private Money actualAmount;
    private ZonedDateTime createdAt;
    public static String UTC = "UTC";

    /**
     * Initializes the account. This method should be called once when creating a new account.
     */
    public void initializeAccount() {
        verifyAccountCreation();
        setId(new AccountId(UUID.randomUUID()));
        this.actualAmount = this.initialAmount;
        this.createdAt = ZonedDateTime.now(ZoneId.of(UTC));
    }

    /**
     * Verifies that the initial amount for the account is greater than zero.
     * Throws an exception if the initial amount is not valid.
     */
    private void verifyAccountCreation() {
        if (!initialAmount.isGreaterThanZero()) {
            throw new AccountsDomainException("Account initial amount must be greater than 0.");
        }
    }

    /**
     * Makes a transaction on the account, deducting the transaction amount from the actual balance.
     * Throws an exception if the transaction amount exceeds the actual balance.
     *
     * @param transactionAmount The amount to deduct from the account.
     */
    public void makeTransaction(Money transactionAmount) {
        verifyTransactionAmount(transactionAmount);
        this.actualAmount = this.actualAmount.subtract(transactionAmount);
    }

    /**
     * Verifies that the transaction amount is valid and does not exceed the actual balance.
     * Throws an exception if the transaction amount is not valid.
     *
     * @param transactionAmount The amount to verify.
     */
    private void verifyTransactionAmount(Money transactionAmount) {
        if (transactionAmount.isGreaterThan(this.actualAmount)) {
            throw new AccountsDomainException(
                    "Transaction amount: " +
                            transactionAmount.getAmount() +
                            " cannot be greater than the actual amount: " + this.actualAmount.getAmount()
            );
        }
    }

    /**
     * Makes a deposit to the account, adding the deposit amount to the actual balance.
     *
     * @param depositAmount The amount to add to the account.
     */
    public void makeDeposit(Money depositAmount) {
        this.actualAmount = this.actualAmount.add(depositAmount);
    }

    public void verifyAccountDeletedEvent() {
        if (this.actualAmount.isGreaterThanZero()) {
            throw new AccountsDomainException("Account cannot be deleted because it currently have money on it.");
        }
    }


    public CustomerId getCustomerId() {
        return customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Money getInitialAmount() {
        return initialAmount;
    }

    public Money getActualAmount() {
        return actualAmount;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Private constructor used by the builder pattern to create an Account instance.
     *
     * @param builder The builder containing account details.
     */
    private Account(Builder builder) {
        customerId = builder.customerId;
        accountNumber = builder.accountNumber;
        initialAmount = builder.initialAmount;
        actualAmount = builder.actualAmount;
        setId(builder.id);
    }


    /**
     * Builder pattern for creating an Account instance.
     */
    public static final class Builder {
        private CustomerId customerId;
        private String accountNumber;
        private Money initialAmount;
        private Money actualAmount;
        private ZonedDateTime createdAt;
        private AccountId id;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
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
         * Sets the {@code accountNumber} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code accountNumber} to set
         * @return a reference to this Builder
         */
        public Builder accountNumber(String val) {
            accountNumber = val;
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
         * Sets the {@code actualAmount} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code actualAmount} to set
         * @return a reference to this Builder
         */
        public Builder actualAmount(Money val) {
            actualAmount = val;
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
         * Returns a {@code Account} built from the parameters previously set.
         *
         * @return a {@code Account} built with parameters of this {@code Account.Builder}
         */
        public Account build() {
            return new Account(this);
        }
    }
}
