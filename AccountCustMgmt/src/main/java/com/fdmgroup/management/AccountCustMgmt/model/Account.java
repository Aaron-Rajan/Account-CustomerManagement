package com.fdmgroup.management.AccountCustMgmt.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ACCOUNT")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "accountId")
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GEN")
    @SequenceGenerator(name = "ACCOUNT_SEQ_GEN", sequenceName = "ACCOUNT_SEQ")
    private long accountId;
    private double balance;
    @ManyToOne
    @JoinColumn(name = "FK_CUSTOMER_NO")
    private Customer customer;

    public Account() {
        super();
    }

    public Account(double balance, Customer customer) {
        super();
        this.balance = balance;
        this.customer = customer;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                ", customer=" + customer +
                '}';
    }
}
