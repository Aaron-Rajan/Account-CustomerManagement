package com.fdmgroup.management.AccountCustMgmt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.management.AccountCustMgmt.controller.CustomerController;
import com.fdmgroup.management.AccountCustMgmt.model.*;
import com.fdmgroup.management.AccountCustMgmt.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerMvcTest {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean
    private CustomerService service;

    @Test
    void getAllCustomers_returnsList() throws Exception {
        Person p = new Person();
        p.setName("Jeffin");
        Address a = new Address();
        a.setCity("Mississauga"); a.setProvince("ON"); a.setPostalCode("L5C 4J7"); a.setStreetNumber("4400");
        p.setAddress(a);

        Company c = new Company();
        c.setName("ACME");

        when(service.getAllCustomers()).thenReturn(List.of(p, c));

        mvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Jeffin")))
                .andExpect(jsonPath("$[1].name", is("ACME")));
    }

    @Test
    void createCustomer_withCustomerDto_returnsOk() throws Exception {
        Address address = new Address("1 King St W", "Toronto", "ON", "M5H 1A1");

        CustomerDto dto = new CustomerDto("ACME Inc", address, "company");

        Company saved = new Company();
        saved.setName("ACME Inc");
        saved.setAddress(address);

        when(service.createCustomer2(any(CustomerDto.class))).thenReturn(saved);

        mvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("ACME Inc")))
                .andExpect(jsonPath("$.address.city", is("Toronto")));
    }

    @Test
    void createCustomer_withGeolocationDto_validationFailure_returns400() throws Exception {
        // Missing "name" (assuming you added @NotBlank on name in GeolocationDto)
        String badJson = """
            {"postalCode":"L5C 4J7","streetNo":"4400","customerType":"person"}
            """;

        mvc.perform(post("/api/customers/geo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCustomer_withGeolocationDto_returnsOk() throws Exception {
        GeolocationDto dto = new GeolocationDto("Jeffin", "L5C 4J7","Beacon Lane" , "4400", "person");

        Person saved = new Person();
        saved.setName("Jeffin");
        Address a = new Address("4400", "L5C 4J7", "Mississauga", "ON");
        saved.setAddress(a);

        when(service.createCustomer(any(GeolocationDto.class))).thenReturn(saved);

        mvc.perform(post("/api/customers/geo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Jeffin")))
                .andExpect(jsonPath("$.address.city", is("Mississauga")));
    }
}

