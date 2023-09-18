package com.rdani2005.yawa.customer.service.domain.entity;

import com.rdani2005.yawa.customer.service.domain.exception.CustomerException;
import com.rdani2005.yawa.domain.entity.AggregateRoot;
import com.rdani2005.yawa.domain.valueobject.CustomerId;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.UUID;

public class Customer  extends AggregateRoot<CustomerId> {
    private final String name;
    private final String lastName;
    private final String identification;
    private final ZonedDateTime birthDay;
    private final ZonedDateTime createdAt;

    public final static  String UTC = "UTC";
    public void initializeCustomer() {
        // Calculates the client's birthday to get the right age of him/her.
        LocalDate birthDate = birthDay.toLocalDate();
        LocalDate currentDate = ZonedDateTime.now().toLocalDate();
        Period period = Period.between(birthDate, currentDate);
        int age = period.getYears();

        // Verify if the client is underage.
        if (age < 18) {
            throw new CustomerException("The client is underage");
        }
        super.setId(new CustomerId(UUID.randomUUID()));
    }


    private Customer(Builder builder) {
        super.setId(builder.id);
        name = builder.name;
        lastName = builder.lastName;
        identification = builder.identification;
        birthDay = builder.birthDay;
        createdAt = builder.createdAt;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIdentification() {
        return identification;
    }

    public ZonedDateTime getBirthDay() {
        return birthDay;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Creates a new instance for Builder pattern
     * @return {@code Builder} class instance
     */
    public static Builder builder() {
        return new Builder();
    }
    /**
     * {@code Customer} builder static inner class.
     */
    public static final class Builder {
        private CustomerId id;
        private String name;
        private String lastName;
        private String identification;
        private ZonedDateTime birthDay;
        private ZonedDateTime createdAt;

        private Builder() {
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
         * Sets the {@code birthDay} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code birthDay} to set
         * @return a reference to this Builder
         */
        public Builder birthDay(ZonedDateTime val) {
            birthDay = val;
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
         * Returns a {@code Customer} built from the parameters previously set.
         *
         * @return a {@code Customer} built with parameters of this {@code Customer.Builder}
         */
        public Customer build() {
            return new Customer(this);
        }
    }
}
