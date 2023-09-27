package com.rdani2005.yawa.transactions.service.domain.entities;

import com.rdani2005.yawa.domain.entity.AggregateRoot;
import com.rdani2005.yawa.domain.valueobject.AccountId;

public class Account extends AggregateRoot<AccountId> {
    private final String accountNumber;

    private Account(Builder builder) {
        setId(builder.id);
        accountNumber = builder.accountNumber;
    }


    /**
     * {@code Account} builder static inner class.
     */
    public static final class Builder {
        private AccountId id;
        private String accountNumber;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
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
         * Returns a {@code Account} built from the parameters previously set.
         *
         * @return a {@code Account} built with parameters of this {@code Account.Builder}
         */
        public Account build() {
            return new Account(this);
        }
    }
}
