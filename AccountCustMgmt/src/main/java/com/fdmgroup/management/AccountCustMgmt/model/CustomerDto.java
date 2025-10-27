package com.fdmgroup.management.AccountCustMgmt.model;

public class CustomerDto {
    private String name;
    private Address address;
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
