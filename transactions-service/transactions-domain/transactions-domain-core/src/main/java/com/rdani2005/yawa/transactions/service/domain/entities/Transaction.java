package com.rdani2005.yawa.transactions.service.domain.entities;

import com.rdani2005.yawa.domain.entity.AggregateRoot;
import com.rdani2005.yawa.domain.valueobject.AccountId;
import com.rdani2005.yawa.domain.valueobject.Money;
import com.rdani2005.yawa.domain.valueobject.TransactionId;
import com.rdani2005.yawa.domain.valueobject.TransactionStatus;
import com.rdani2005.yawa.transactions.service.domain.exception.TransactionsDomainException;
import com.rdani2005.yawa.transactions.service.domain.valueobjects.TransactionTrackingId;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

public class Transaction extends AggregateRoot<TransactionId> {
    private final AccountId accountId;
    private final Money transactionAmount;
    private TransactionTrackingId transactionTrackingId;
    private TransactionStatus transactionStatus;
    private ZonedDateTime createdAt;
    public static String UTC = "UTC";

    private Transaction(Builder builder) {
        setId(builder.id);
        accountId = builder.accountId;
        transactionAmount = builder.transactionAmount;
        transactionStatus = builder.transactionStatus;
        createdAt = builder.createdAt;
    }

    public void initializeTransaction() {
        validateTransactionInitialAmount();
        transactionStatus = TransactionStatus.PROCESING;
        this.createdAt = ZonedDateTime.now(ZoneId.of(UTC));
        this.transactionTrackingId = new TransactionTrackingId(UUID.randomUUID());
    }

    public void acceptTransaction() {
        if (transactionStatus != TransactionStatus.PROCESING) {
            throw new TransactionsDomainException("Transaction must be processing before accept it.");
        }
        this.transactionStatus = TransactionStatus.ACCEPTED;
    }

    public void denyTransaction() {
        if (transactionStatus != TransactionStatus.PROCESING) {
            throw new TransactionsDomainException("Transaction must be processing before deny it.");
        }
        this.transactionStatus = TransactionStatus.DENIED;
    }

    public void transactionFailed() {
        this.transactionStatus = TransactionStatus.FAILED;
    }

    private void validateTransactionInitialAmount() {
        if (!this.transactionAmount.isGreaterThanZero()) {
            throw new TransactionsDomainException("Transaction amount must be greater than 0.");
        }
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public Money getTransactionAmount() {
        return transactionAmount;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public static Builder builder() {
        return new Builder();
    }


    /**
     * {@code Transaction} builder static inner class.
     */
    public static final class Builder {
        private TransactionId id;
        private AccountId accountId;
        private Money transactionAmount;
        private TransactionStatus transactionStatus;
        private ZonedDateTime createdAt;

        private Builder() {
        }

        /**
         * Sets the {@code id} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code id} to set
         * @return a reference to this Builder
         */
        public Builder id(TransactionId val) {
            id = val;
            return this;
        }

        /**
         * Sets the {@code accountId} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code accountId} to set
         * @return a reference to this Builder
         */
        public Builder accountId(AccountId val) {
            accountId = val;
            return this;
        }

        /**
         * Sets the {@code transactionAmount} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code transactionAmount} to set
         * @return a reference to this Builder
         */
        public Builder transactionAmount(Money val) {
            transactionAmount = val;
            return this;
        }

        /**
         * Sets the {@code transactionStatus} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code transactionStatus} to set
         * @return a reference to this Builder
         */
        public Builder transactionStatus(TransactionStatus val) {
            transactionStatus = val;
            return this;
        }

        public Builder createdAt(ZonedDateTime val) {
            createdAt = val;
            return this;
        }

        /**
         * Returns a {@code Transaction} built from the parameters previously set.
         *
         * @return a {@code Transaction} built with parameters of this {@code Transaction.Builder}
         */
        public Transaction build() {
            return new Transaction(this);
        }
    }
}
