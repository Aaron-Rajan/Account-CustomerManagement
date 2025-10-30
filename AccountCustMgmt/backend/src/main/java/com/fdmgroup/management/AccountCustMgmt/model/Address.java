package com.fdmgroup.management.AccountCustMgmt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class Address {
    @Id
    @UuidGenerator
    private String addressId;
    @Column
    @NotBlank(message = "Street number can not be blank")
    private String streetNumber;
    @Column
    @NotBlank(message = "Postal code can not be blank")
    private String postalCode;
    @Column
    @NotBlank(message = "City can not be blank")
    private String city;
    @Column
    @NotBlank(message = "Province can not be blank")
    private String province;
    @OneToOne(mappedBy = "address")
    private Customer customer;

    public Address(String streetNumber, String postalCode, String city, String province) {
        super();
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.province = province;
    }

    public Address() {
        super();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }


    @Override
    public String toString() {
        return "Address{" +
                "addressId='" + addressId + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                '}';
    }
}
