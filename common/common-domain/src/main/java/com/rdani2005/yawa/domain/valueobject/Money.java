package com.rdani2005.yawa.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Represents a monetary amount with support for basic operations.
 */
public class Money {
    private final BigDecimal amount;
    public static final Money ZERO = new Money(BigDecimal.ZERO);

    /**
     * Constructs a new Money object with the specified amount.
     *
     * @param amount The monetary amount as a BigDecimal.
     */
    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets the monetary amount as a BigDecimal.
     *
     * @return The monetary amount.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Checks if the monetary amount is greater than zero.
     *
     * @return true if the amount is greater than zero, false otherwise.
     */
    public boolean isGreaterThanZero() {
        return this.amount != null && this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Checks if the monetary amount is greater than another Money object.
     *
     * @param money The Money object to compare against.
     * @return true if this amount is greater than the provided Money, false otherwise.
     */
    public boolean isGreaterThan(Money money) {
        return this.amount != null && this.amount.compareTo(money.getAmount()) > 0;
    }

    /**
     * Subtracts another Money object from this Money object.
     *
     * @param money The Money object to subtract.
     * @return A new Money object representing the result of the subtraction.
     */
    public Money subtract(Money money) {
        return new Money(setScale(this.amount.subtract(money.getAmount())));
    }

    /**
     * Adds another Money object to this Money object.
     *
     * @param money The Money object to add.
     * @return A new Money object representing the result of the addition.
     */
    public Money add(Money money) {
        return new Money(setScale(this.amount.add(money.getAmount())));
    }

    /**
     * Multiplies this Money object by an integer multiplier.
     *
     * @param multiplier The integer multiplier.
     * @return A new Money object representing the result of the multiplication.
     */
    public Money multiply(int multiplier) {
        return new Money(setScale(this.amount.multiply(new BigDecimal(multiplier))));
    }

    /**
     * Sets the scale of the BigDecimal amount to two decimal places, using rounding mode HALF_EVEN.
     *
     * @param input The BigDecimal amount to set the scale for.
     * @return A BigDecimal with the scale set to two decimal places.
     */
    private BigDecimal setScale(BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * Checks if this Money object is equal to another object.
     *
     * @param o The object to compare against.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    /**
     * Computes the hash code of this Money object.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}

