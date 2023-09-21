package com.rdani2005.yawa.accounts.service.domain.entity;

import com.rdani2005.yawa.domain.entity.AggregateRoot;
import com.rdani2005.yawa.domain.valueobject.CustomerId;

/**
 * Represents a Customer entity in the domain.
 * Extends the AggregateRoot class, using CustomerId as the aggregate root identifier.
 */
public class Customer extends AggregateRoot<CustomerId> {
    private String identification;
    private String name;
    private String lastName;

    private Customer(Builder builder) {
        identification = builder.identification;
        name = builder.name;
        lastName = builder.lastName;
        setId(builder.id);
    }

    /**
     * Get the identification of the customer.
     *
     * @return The identification of the customer.
     */
    public String getIdentification() {
        return identification;
    }

    /**
     * Get the name of the customer.
     *
     * @return The name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the last name of the customer.
     *
     * @return The last name of the customer.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Create a new builder for building a Customer instance.
     *
     * @return A new instance of the Customer.Builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * {@code Customer} builder static inner class.
     */
    public static final class Builder {
        private String identification;
        private String name;
        private String lastName;
        private CustomerId id;

        private Builder() {
        }

        /**
         * Sets the {@code identification} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code identification} to set
         * @return a reference to this Builder
         */
        public Builder identification(String val) {
            identification = val;
            return this;
        }

        /**
         * Sets the {@code name} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code name} to set
         * @return a reference to this Builder
         */
        public Builder name(String val) {
            name = val;
            return this;
        }

        /**
         * Sets the {@code lastName} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code lastName} to set
         * @return a reference to this Builder
         */
        public Builder lastName(String val) {
            lastName = val;
            return this;
        }

        /**
         * Sets the {@code id} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code id} to set
         * @return a reference to this Builder
         */
        public Builder id(CustomerId val) {
            id = val;
            return this;
        }

        /**
         * Returns a {@code Customer} built from the parameters previously set.
         *
         * @return a {@code Customer} built with parameters of this {@code Customer.Builder}
         */
        public Customer build() {
            return new Customer(this);
        }
    }
}
