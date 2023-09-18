package com.rdani2005.yawa.customer.service.dataaccess.customer.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * The {@code CustomerEntity} class represents the persistent entity for customer data in the database.
 * It is mapped to the "customers" table in the database and includes fields for customer information such as name,
 * last name, identification, birthdate, and creation timestamp.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
@Entity
public class CustomerEntity {
    @Id
    private UUID id;
    private String name;
    private String lastName;
    private String identification;
    private ZonedDateTime birthDay;
    private ZonedDateTime createdAt;
}
