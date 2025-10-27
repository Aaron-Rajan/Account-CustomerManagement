package com.fdmgroup.management.AccountCustMgmt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "PersonJoined")
public class Person extends Customer {
    public Person() {
        super();
    }

    public Person(String name, Address address) {
        super(name, address);
    }
}
