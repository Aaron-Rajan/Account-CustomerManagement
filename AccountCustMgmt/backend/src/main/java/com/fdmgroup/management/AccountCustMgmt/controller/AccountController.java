package com.fdmgroup.management.AccountCustMgmt.controller;

import com.fdmgroup.management.AccountCustMgmt.model.Account;
import com.fdmgroup.management.AccountCustMgmt.model.AccountDto;
import com.fdmgroup.management.AccountCustMgmt.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        super();
        this.service = service;
    }

    @Operation(summary = "Creating new account")
    @PostMapping
    public ResponseEntity<Account> addAccount(@Valid @RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(service.createAccount(accountDto));
    }

    @Operation(summary = "Getting all accounts")
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(service.getAllAccounts());
    }

    @Operation(summary = "Getting all accounts whose city is Toronto")
    @GetMapping("/TorontoAccounts")
    public ResponseEntity<List<Account>> getAllTorontoAccounts() {
        return ResponseEntity.ok(service.getAllTorontoAccounts());
    }

    @Operation(summary = "Getting all accounts whose city is City")
    @GetMapping("/{city}/accounts")
    public ResponseEntity<List<Account>> getAllAccountsByCity(@PathVariable String city) {
        return ResponseEntity.ok(service.getAllAccountsByCity(city));
    }

    @Operation(summary = "Updating account by ID")
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable long id, @Valid @RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(service.updateAccount(id, accountDto));
    }

    @Operation(summary = "Deleting account by ID")
    @DeleteMapping("{id}")
    public void deleteAccount(@PathVariable long id) {
        service.deleteAccount(id);
    }
}
