package com.fdmgroup.management.AccountCustMgmt.services;

import com.fdmgroup.management.AccountCustMgmt.exceptions.AccountNotFoundException;
import com.fdmgroup.management.AccountCustMgmt.exceptions.CustomerNotFoundException;
import com.fdmgroup.management.AccountCustMgmt.model.*;
import com.fdmgroup.management.AccountCustMgmt.repository.AccountRepository;
import com.fdmgroup.management.AccountCustMgmt.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepo;
    private final GeolocationClient geoClient;
    private final AccountRepository accountRepo;

    public CustomerService(CustomerRepository customerRepo, GeolocationClient geoClient, AccountRepository accountRepo) {
        this.customerRepo = customerRepo;
        this.geoClient = geoClient;
        this.accountRepo = accountRepo;
    }

    public Customer findCustomerById(long id) {
        return customerRepo.findById(id).get();
    }

    public Customer createCustomer(GeolocationDto geolocationDto) {
        Geolocation geoResp = geoClient.getLocation(geolocationDto.postalCode());
        Customer customer = null;
        if (geolocationDto.customerType().equals("person")) {
            customer = new Person();
        } else if (geolocationDto.customerType().equals("company")) {
            customer = new Company();
        }
        Address address = new Address();
        customer.setName(geolocationDto.name());
        address.setPostalCode(geolocationDto.postalCode());
        address.setStreetNumber(geolocationDto.streetNo());
        address.setCity(geoResp.getCity());
        address.setProvince(geoResp.getProvince());
        customer.setAddress(address);
        return customerRepo.save(customer);
    }

    public Customer createCustomer2(CustomerDto customerDto) {
        Customer customer = null;
        if (customerDto.getCustomerType().equals("person")) {
            customer = new Person();
        } else if (customerDto.getCustomerType().equals("company")) {
            customer = new Company();
        }
        BeanUtils.copyProperties(customerDto, customer);
        return customerRepo.save(customer);
    }

    //Updates name, postal code, city, and province
    public Customer updateCustomer(CustomerDto customerDto, long id) {
        Optional<Customer> optCustomerToBeUpdated = customerRepo.findById(id);
        Customer customerToBeUpdated = null;
        Address address = new Address();
        if (optCustomerToBeUpdated.isPresent()) {
            customerToBeUpdated = optCustomerToBeUpdated.get();
        } else {
            if (customerDto.getCustomerType().equals("person")) {
                customerToBeUpdated = new Person();
            } else if (customerDto.getCustomerType().equals("company")) {
                customerToBeUpdated = new Company();
            }
        }
        customerToBeUpdated.setName(customerDto.getName());
        address.setPostalCode(customerDto.getAddress().getPostalCode());
        address.setCity(customerDto.getAddress().getCity());
        address.setStreetNumber(customerDto.getAddress().getStreetNumber());
        address.setProvince((customerDto.getAddress().getProvince()));
        customerToBeUpdated.setAddress(address);
        return customerRepo.save(customerToBeUpdated);
    }

    public void deleteCustomer(long id) {
        customerRepo.deleteById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public void setAccount(long customerId, long accountId) {
        Customer customerReceieved = retrieveEmployeeById(customerId);
        Account accountReceieved = accountRepo.findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found"));
        customerReceieved.getAccounts().add(accountReceieved);
        accountReceieved.setCustomer(customerReceieved);
        customerRepo.save(customerReceieved);
    }

    public Customer retrieveEmployeeById(long customerId) {
        return customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    public List<Account> getAllTorontoAccounts(String city) {
        return customerRepo.getAllCityAccounts("Toronto");
    }
}
