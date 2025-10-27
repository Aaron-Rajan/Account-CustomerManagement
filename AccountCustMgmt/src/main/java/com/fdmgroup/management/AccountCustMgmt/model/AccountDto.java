package com.fdmgroup.management.AccountCustMgmt.model;

public class AccountDto {
    private double balance;
    private Customer customer;
    private double interestRate;
    private String accountType;

    public AccountDto() {
    }

    public AccountDto(double balance, Customer customer, double interestRate, String accountType) {
        this.balance = balance;
        this.customer = customer;
        this.interestRate = interestRate;
        this.accountType = accountType;
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

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
