package com.fdmgroup.management.AccountCustMgmt.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "company")
//@Table(name = "CompanyJoined")
public class Company extends Customer {
    public Company(String name, Address address) {
        super(name, address);
    }

    public Company() {
        super();
    }
}
