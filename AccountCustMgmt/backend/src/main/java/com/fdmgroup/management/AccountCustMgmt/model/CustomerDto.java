package com.fdmgroup.management.AccountCustMgmt.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CustomerDto {
    @Column
    @NotNull(message = "Name is required")
    private String name;
    @Column
    @NotNull(message = "Address is required")
    private Address address;
    @Column
    @NotNull(message = "Customer Type is required")
    @Pattern(regexp = "person|company", flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Customer type must be 'person' or 'company'")
    private String customerType;

    public CustomerDto(String name, Address address, String customerType) {
        super();
        this.name = name;
        this.address = address;
        this.customerType = customerType;
    }

    public CustomerDto() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}
