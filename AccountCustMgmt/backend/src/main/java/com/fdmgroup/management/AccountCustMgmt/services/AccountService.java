package com.fdmgroup.management.AccountCustMgmt.services;

import com.fdmgroup.management.AccountCustMgmt.model.Account;
import com.fdmgroup.management.AccountCustMgmt.model.AccountDto;
import com.fdmgroup.management.AccountCustMgmt.model.CheckingAccount;
import com.fdmgroup.management.AccountCustMgmt.model.SavingsAccount;
import com.fdmgroup.management.AccountCustMgmt.repository.AccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepo;

    public AccountService(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Account createAccount(AccountDto accountDto) {
        Account account = null;
        if (accountDto.getAccountType().equals("savings")) {
            account = new SavingsAccount();
        } else if (accountDto.getAccountType().equals("checking")) {
            account = new CheckingAccount(accountDto.getBalance(), null);
        }
        if (account != null) {
            BeanUtils.copyProperties(accountDto, account, "nextCheckNumber");
            return accountRepo.save(account);
        } else {
            return account;
            // Logging erorr
        }
//        return accountRepo.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    public List<Account> getAllTorontoAccounts() {
        return accountRepo.getAllCityAccounts("Toronto");
    }
    public List<Account> getAllAccountsByCity(String city) {
        return accountRepo.getAllCityAccounts(city);
    }

    public Account updateAccount(long id, AccountDto accountDto) {
        Account accountToBeUpdated = accountRepo.findById(id).get();
        BeanUtils.copyProperties(accountDto, accountToBeUpdated);
        return accountRepo.save(accountToBeUpdated);
    }

    public void deleteAccount(long id) {
        accountRepo.deleteById(id);
    }
}
