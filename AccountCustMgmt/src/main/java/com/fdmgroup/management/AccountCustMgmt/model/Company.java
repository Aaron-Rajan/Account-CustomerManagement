package com.fdmgroup.management.AccountCustMgmt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "CompanyJoined")
public class Company extends Customer {
    public Company(String name, Address address) {
        super(name, address);
    }

    public Company() {
        super();
    }
}
