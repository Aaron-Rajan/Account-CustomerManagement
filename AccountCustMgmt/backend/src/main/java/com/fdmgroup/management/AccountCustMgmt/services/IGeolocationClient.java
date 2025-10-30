package com.fdmgroup.management.AccountCustMgmt.services;

import com.fdmgroup.management.AccountCustMgmt.model.Geolocation;

public interface IGeolocationClient {
    Geolocation getLocation(String postalCode);
}
