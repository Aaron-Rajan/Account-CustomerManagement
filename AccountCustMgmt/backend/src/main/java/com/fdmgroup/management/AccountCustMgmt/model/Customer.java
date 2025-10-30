package com.fdmgroup.management.AccountCustMgmt.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Customer_Type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "CUSTOMER")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "customerId")
public abstract class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_SEQ_GEN")
    @SequenceGenerator(name = "CUSTOMER_SEQ_GEN", sequenceName = "CUSTOMER_SEQ")
    private long customerId;
    private String name;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "FK_ADDRESS_NO")
    private Address address;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Account> accounts;

    public Customer(String name, Address address, List<Account> accounts) {
        super();
        this.name = name;
        this.address = address;
        this.accounts = accounts;
    }

    public Customer(String name, Address address) {
        super();
        this.name = name;
        this.address = address;
    }

    public Customer() {
        super();
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", accounts=" + accounts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Customer customer)) return false;
        return customerId == customer.customerId && Objects.equals(name, customer.name) && Objects.equals(address, customer.address) && Objects.equals(accounts, customer.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name, address, accounts);
    }
}
