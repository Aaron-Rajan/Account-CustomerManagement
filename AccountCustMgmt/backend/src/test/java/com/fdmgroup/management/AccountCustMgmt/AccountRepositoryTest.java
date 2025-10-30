package com.fdmgroup.management.AccountCustMgmt;

import com.fdmgroup.management.AccountCustMgmt.model.*;
import com.fdmgroup.management.AccountCustMgmt.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test") // optional, in case you gate seeders/runners by profile
class AccountRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("getAllCityAccounts returns only accounts whose customer.address.city matches (case-insensitive)")
    void getAllCityAccounts_filtersByCity_ignoreCase() {
        // --- Arrange (persist entities) ---
        // A Toronto account
        Address addrToronto = new Address("4400", "L5C 4J7", "Toronto", "ON");
        Customer personToronto = new Person("Alice", addrToronto);
        SavingsAccount accToronto = new SavingsAccount();
        accToronto.setBalance(2000.0);
        accToronto.setInterestRate(2.0);
        accToronto.setCustomer(personToronto);

        // A Mississauga account
        Address addrMiss = new Address("123", "L5B 1A1", "Mississauga", "ON");
        Customer personMiss = new Person("Bob", addrMiss);
        SavingsAccount accMiss = new SavingsAccount();
        accMiss.setBalance(1500.0);
        accMiss.setInterestRate(1.5);
        accMiss.setCustomer(personMiss);

        // Another Toronto account
        Address addrToronto2 = new Address("12", "M5V 2T6", "Toronto", "ON");
        Customer personToronto2 = new Person("Carol", addrToronto2);
        CheckingAccount accToronto2 = new CheckingAccount();
        accToronto2.setBalance(800.0);
        accToronto2.setCustomer(personToronto2);

        // Persist graph.
        em.persist(addrToronto);
        em.persist(personToronto);
        em.persist(accToronto);

        em.persist(addrMiss);
        em.persist(personMiss);
        em.persist(accMiss);

        em.persist(addrToronto2);
        em.persist(personToronto2);
        em.persist(accToronto2);

        em.flush();
        em.clear(); // ensure we actually hit the DB, not the persistence context

        // --- Act ---
        List<Account> resultLower = accountRepository.getAllCityAccounts("toronto");
        List<Account> resultMixed = accountRepository.getAllCityAccounts("ToRoNtO");

        // --- Assert ---
        assertThat(resultLower).hasSize(2);
        assertThat(resultLower)
                .allSatisfy(a ->
                        assertThat(a.getCustomer().getAddress().getCity()).matches(c -> c.equalsIgnoreCase("toronto"))
                );

        // case-insensitive behavior
        assertThat(resultMixed).hasSize(2);
        assertThat(resultMixed).extracting(a -> a.getCustomer().getAddress().getCity())
                .allMatch(c -> ((String) c).equalsIgnoreCase("toronto"));
    }

    @Test
    @DisplayName("getAllCityAccounts returns empty list when no matching city")
    void getAllCityAccounts_noMatches_returnsEmpty() {
        Address addr = new Address("1", "A1A 1A1", "Ottawa", "ON");
        Customer person = new Person("Dave", addr);
        SavingsAccount acc = new SavingsAccount();
        acc.setBalance(500.0);
        acc.setInterestRate(1.0);
        acc.setCustomer(person);

        em.persist(addr);
        em.persist(person);
        em.persist(acc);
        em.flush();
        em.clear();

        List<Account> result = accountRepository.getAllCityAccounts("Toronto");
        assertThat(result).isEmpty();
    }
}

