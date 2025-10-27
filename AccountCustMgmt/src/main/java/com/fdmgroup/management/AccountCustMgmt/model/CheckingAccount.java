package com.fdmgroup.management.AccountCustMgmt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "CHECKING_ACCOUNT")
public class CheckingAccount extends Account {
    private int nextCheckNumber = 1;

    public CheckingAccount() {
        super();
    }

    public CheckingAccount(double balance, Customer customer) {
        super(balance, customer);
    }

    public int getNextCheckNumber() {
        return nextCheckNumber++;
    }
    public void setNextCheckNumber(int nextCheckNumber) {
        this.nextCheckNumber = nextCheckNumber;
    }
}
