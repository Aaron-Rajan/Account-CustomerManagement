package com.fdmgroup.management.AccountCustMgmt.repository;

import com.fdmgroup.management.AccountCustMgmt.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
