package com.fdmgroup.management.AccountCustMgmt.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "person")
//@Table(name = "PersonJoined")
public class Person extends Customer {
    public Person() {
        super();
    }

    public Person(String name, Address address) {
        super(name, address);
    }


}
