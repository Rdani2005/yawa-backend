package com.rdani2005.yawa.accounts.service.dataaccess.account.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Represents an entity for storing account information in the database.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@Entity
public class AccountEntity {
    @Id
    private UUID id;
    private UUID customerId;
    private String accountNumber;
    private BigDecimal initialAmount;
    private BigDecimal actualAmount;
    private ZonedDateTime createdAt;
}
