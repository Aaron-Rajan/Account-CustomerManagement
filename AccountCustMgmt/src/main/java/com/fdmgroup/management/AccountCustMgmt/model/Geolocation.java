package com.fdmgroup.management.AccountCustMgmt.model;

public class Geolocation {
    private String city;
    private String province;

    public Geolocation(String city, String province) {
        super();
        this.city = city;
        this.province = province;
    }

    @Override
    public String toString() {
        return "Geolocation{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
