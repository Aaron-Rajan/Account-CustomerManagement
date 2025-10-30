package com.fdmgroup.management.AccountCustMgmt.repository;

import com.fdmgroup.management.AccountCustMgmt.model.Account;
import com.fdmgroup.management.AccountCustMgmt.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c.accounts FROM Customer c WHERE lower(c.address.city)=lower(:city)")
    List<Account> getAllCityAccounts(@Param("city") String city);
}
