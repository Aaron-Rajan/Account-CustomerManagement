package com.fdmgroup.management.AccountCustMgmt.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AccountDto {
    @NotNull(message = "Balance is required")
    private double balance;
    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Interest rate cannot be negative")
    private double interestRate;
    @NotBlank(message = "Account type is required")
    @Pattern(regexp = "checking|savings", flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Account type must be 'checking' or 'savings'")
    private String accountType;

    public AccountDto() {
    }

    public AccountDto(double balance, double interestRate, String accountType) {
        this.balance = balance;
        this.interestRate = interestRate;
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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
