package com.fdmgroup.management.AccountCustMgmt;


import com.fdmgroup.management.AccountCustMgmt.model.Account;
import com.fdmgroup.management.AccountCustMgmt.model.AccountDto;
import com.fdmgroup.management.AccountCustMgmt.model.CheckingAccount;
import com.fdmgroup.management.AccountCustMgmt.model.SavingsAccount;
import com.fdmgroup.management.AccountCustMgmt.repository.AccountRepository;
import com.fdmgroup.management.AccountCustMgmt.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository repo;
    private AccountService service;


    @BeforeEach
    void setup() {
        service = new AccountService(repo);
    }

    @Test
    public void testRepo_ReturnsTorontoCity() {
        Account a1 = new SavingsAccount();
        Account a2 = new CheckingAccount();
        when(repo.getAllCityAccounts("Toronto")).thenReturn(List.of(a1, a2));
        List<Account> actualResult = service.getAllTorontoAccounts();
        assertEquals(2, actualResult.size());
        verify(repo, times(1)).getAllCityAccounts("Toronto");
    }

    @Test
    public void testService_ReturnsSavingsAccount_WhenCreated() {
        AccountDto accountDto = new AccountDto(1000, null, 2.0, "savings");
        Account savingsAccount = new SavingsAccount(1000, null, 2.0);
        when(repo.save(any(Account.class))).thenReturn(savingsAccount);
        Account actualResult = service.createAccount(accountDto);
        assertThat(actualResult).isInstanceOf(SavingsAccount.class);
        verify(repo, times(1)).save(any(Account.class));
    }

    @Test
    public void testService_UpdatesAccount_WhenCalled() {
        SavingsAccount existing = new SavingsAccount(500, null, 1.5);
        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        // save returns whatever it receives
        when(repo.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

        AccountDto accountDto = new AccountDto(1000, null, 2.0, "savings");

        // Act
        Account updated = service.updateAccount(1L, accountDto);

        // Assert
        assertTrue(updated instanceof SavingsAccount);
        assertEquals(1000, updated.getBalance());
        assertEquals(2.0, ((SavingsAccount) updated).getInterestRate());

        // The same instance was updated and saved
        verify(repo).findById(1L);
        verify(repo).save(existing);
    }
}
