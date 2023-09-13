package com.rdani2005.yawa.saga;

/**
 * Interface representing a step in a saga.
 * A saga step defines the actions to be taken during the normal
 * processing and the rollback process in case of failures.
 *
 * @param <T> The type of data required by the saga step.
 */
public interface SagaStep<T> {

    /**
     * Process the saga step.
     * This method defines the actions to be executed as part of the
     * normal processing of the saga step.
     *
     * @param data The data required by the saga step.
     */
    void process(T data);

    /**
     * Rollback the saga step in case of failures.
     * This method defines the actions to be executed as part of the
     * rollback process in case the saga step encounters failures.
     *
     * @param data The data required by the saga step for rollback.
     */
    void rollback(T data);
}
