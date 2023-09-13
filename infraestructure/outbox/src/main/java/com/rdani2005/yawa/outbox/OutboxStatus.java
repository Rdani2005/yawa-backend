package com.rdani2005.yawa.outbox;

/**
 * Enumeration representing the status of an outbox message or process.
 * The status can indicate whether the process or message is started, completed,
 * or has encountered a failure.
 */
public enum OutboxStatus {
    /**
     * Represents the initial state when a process or message is started but not yet completed.
     */
    STARTED,

    /**
     * Indicates that the process or message has been successfully completed without any issues.
     */
    COMPLETED,

    /**
     * Signifies that the process or message has failed or encountered an error during execution.
     */
    FAILED
}
