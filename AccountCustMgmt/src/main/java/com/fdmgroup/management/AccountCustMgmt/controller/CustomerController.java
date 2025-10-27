package com.fdmgroup.management.AccountCustMgmt.controller;

import com.fdmgroup.management.AccountCustMgmt.model.Customer;
import com.fdmgroup.management.AccountCustMgmt.model.CustomerDto;
import com.fdmgroup.management.AccountCustMgmt.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @Operation(summary = "Gets customer by specified id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE)
                    },
                    headers = {
                            @Header(name = "location", description = "URL to particular id")
                    },
                    description = "Get Contact by id"
            ),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable long id) {
        return ResponseEntity.ok(service.findCustomerById(id));
    }

//    @PostMapping()
//    public Customer createCustomer(@RequestBody Customer customer) {
//        return service.createCustomer(customer);
//    }
//    @Operation(summary = "Creating new customer")
//    @PostMapping()
//    public ResponseEntity<Customer> createCustomer(@RequestBody String name, @RequestBody String postalCode, @RequestBody String streetName, @RequestBody String customerType) {
//        return ResponseEntity.ok(service.createCustomer(name, postalCode, streetName, customerType));
//    }

    @Operation(summary = "Creating new customer")
    @PostMapping()
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(service.createCustomer2(customerDto));
    }

    @Operation(summary = "Updating customer by ID")
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable long id, @Valid @RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(service.updateCustomer(customerDto, id));
    }

    @Operation(summary = "Deleting customer by ID")
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable long id) {
        service.deleteCustomer(id);
    }

    @Operation(summary = "Getting all customers")
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(service.getAllCustomers());
    }

    @Operation(summary = "Setting account to customer")
    @PostMapping("/setAccount")
    public void setAccount(@RequestParam long customerId, @RequestParam long accountId) {
        service.setAccount(customerId, accountId);
    }

}
