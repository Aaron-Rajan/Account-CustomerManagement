package com.fdmgroup.management.AccountCustMgmt.model;

import jakarta.persistence.*;

@Entity
@Table(name = "SAVINGS_ACCOUNT")
public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(double balance, Customer customer, double interestRate) {
        super(balance, customer);
        this.interestRate = interestRate;
    }

    public SavingsAccount() {
        super();
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
