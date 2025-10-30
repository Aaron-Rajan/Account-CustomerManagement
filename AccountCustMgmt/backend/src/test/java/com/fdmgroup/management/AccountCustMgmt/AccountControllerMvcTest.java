package com.fdmgroup.management.AccountCustMgmt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.management.AccountCustMgmt.controller.AccountController;
import com.fdmgroup.management.AccountCustMgmt.model.*;
import com.fdmgroup.management.AccountCustMgmt.services.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountController.class)
class AccountControllerMvcTest {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean
    private AccountService service; // <-- mocked service the controller uses

    @Test
    void getAllAccounts_returnsList() throws Exception {
        SavingsAccount a1 = new SavingsAccount();
        a1.setBalance(1000);
        a1.setInterestRate(2.5);

        CheckingAccount a2 = new CheckingAccount();
        a2.setBalance(500);
        a2.setNextCheckNumber(1);

        when(service.getAllAccounts()).thenReturn(List.of(a1, a2));

        mvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].balance", is(1000.0)))
                .andExpect(jsonPath("$[1].balance", is(500.0)));
    }

    @Test
    void createAccount_returnsOk_withSavings() throws Exception {
        // DTO you accept at the controller
        AccountDto dto = new AccountDto(1000.0, 2.5, "savings");

        // What the service returns
        SavingsAccount saved = new SavingsAccount();
        saved.setBalance(1000.0);
        saved.setInterestRate(2.5);

        when(service.createAccount(any(AccountDto.class))).thenReturn(saved);

        mvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk()) // use isCreated() if your controller returns 201
                .andExpect(jsonPath("$.balance", is(1000.0)))
                .andExpect(jsonPath("$.interestRate", is(2.5)));
    }

    @Test
    void createAccount_validationFailure_returns400() throws Exception {
        // Missing accountType (assuming @NotBlank on accountType in AccountDto)
        String badJson = """
            {"balance": 1000.0, "interestRate": 2.5}
            """;

        mvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateAccount_returnsOk() throws Exception {
        AccountDto dto = new AccountDto(1500.0, 3.0, "savings");

        SavingsAccount updated = new SavingsAccount();
        updated.setBalance(1500.0);
        updated.setInterestRate(3.0);

        when(service.updateAccount(eq(5L), any(AccountDto.class))).thenReturn(updated);

        mvc.perform(put("/api/accounts/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance", is(1500.0)))
                .andExpect(jsonPath("$.interestRate", is(3.0)));
    }

    @Test
    void getTorontoAccounts_returnsList() throws Exception {
        // Adjust the path if your controller uses a different mapping.
        SavingsAccount s = new SavingsAccount();
        s.setBalance(2000.0);
        s.setInterestRate(2.0);
        Address toronto = new Address("4400", "L5C 4J7", "Toronto", "ON");
        Customer aaron = new Person("Aaron", toronto);
        aaron.setCustomerId(1L);
        s.setCustomer(aaron);

        when(service.getAllTorontoAccounts()).thenReturn(List.of(s));

        mvc.perform(get("/api/accounts/TorontoAccounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].balance", is(2000.0)))
                .andExpect(jsonPath("$[0].customer", is(1)));
    }
}

