package com.fdmgroup.management.AccountCustMgmt;

import com.fdmgroup.management.AccountCustMgmt.model.*;
import com.fdmgroup.management.AccountCustMgmt.repository.AccountRepository;
import com.fdmgroup.management.AccountCustMgmt.repository.CustomerRepository;
import com.fdmgroup.management.AccountCustMgmt.exceptions.CustomerNotFoundException;
import com.fdmgroup.management.AccountCustMgmt.services.CustomerService;
import com.fdmgroup.management.AccountCustMgmt.services.GeolocationClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock private CustomerRepository customerRepo;
    @Mock private GeolocationClient geoClient;
    @Mock private AccountRepository accountRepo;

    private CustomerService service;

    @BeforeEach
    void setup() {
        service = new CustomerService(customerRepo, geoClient, accountRepo);
    }

    @Test
    void testFindCustomerById_delegatesToRepo() {
        Person existing = new Person();
        when(customerRepo.findById(1L)).thenReturn(Optional.of(existing));

        Customer result = service.findCustomerById(1L);

        assertThat(result).isSameAs(existing);
    }

    @Test
    void testCreateCustomer_UsesGeo_and_SavesPerson() {
        GeolocationDto dto = new GeolocationDto("Jeffin", "L5C 4J7", "Beacon Lane", "4400", "person");
        Geolocation geo = new Geolocation("Mississauga", "ON");

        when(geoClient.getLocation("L5C 4J7")).thenReturn(geo);
        when(customerRepo.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Customer saved = service.createCustomer(dto);

        Address expectedAddress = new Address("4400", "L5C 4J7", "Mississauga", "ON");
//        Customer expectedResult = new Person("Jeffin", expectedAddress);

//        assertEquals(saved, expectedResult);

        assertThat(saved).isInstanceOf(Person.class);
        assertEquals("Jeffin", saved.getName());
        Address addr = saved.getAddress();
        assertThat(addr).isNotNull();
        assertEquals("4400", addr.getStreetNumber());
        assertEquals("Mississauga", addr.getCity());
        assertEquals("ON", addr.getProvince());

        verify(geoClient).getLocation("L5C 4J7");
        verify(customerRepo).save(any(Customer.class));
    }

    @Test
    void testCreateCustomer2_SavesCompany() {
        // Arrange: build Address entity and CustomerDto
        Address address = new Address();
        address.setStreetNumber("1 King St W");
        address.setCity("Toronto");
        address.setProvince("ON");
        address.setPostalCode("M5H 1A1");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerType("company");
        customerDto.setName("ACME Inc");
        customerDto.setAddress(address);

        when(customerRepo.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Customer saved = service.createCustomer2(customerDto);

        // Assert
        assertThat(saved).isInstanceOf(Company.class);
        assertEquals("ACME Inc", saved.getName());
        assertThat(saved.getAddress()).isNotNull();
        assertEquals("1 King St W", saved.getAddress().getStreetNumber());
        assertEquals("Toronto", saved.getAddress().getCity());
        assertEquals("ON", saved.getAddress().getProvince());
        assertEquals("M5H 1A1", saved.getAddress().getPostalCode());
        verify(customerRepo).save(any(Customer.class));
    }

    @Test
    void testUpdateCustomer_UpdatesExisting() {
        Person existing = new Person();
        existing.setAddress(new Address());

        when(customerRepo.findById(5L)).thenReturn(Optional.of(existing));
        when(customerRepo.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Address address = new Address();
        address.setPostalCode("L5C 4J7");
        address.setCity("Mississauga");
        address.setStreetNumber("4400");
        address.setProvince("ON");

        CustomerDto dto = new CustomerDto();
        dto.setCustomerType("person");
        dto.setName("New Name");
        dto.setAddress(address);

        Customer updated = service.updateCustomer(dto, 5L);

        assertEquals("New Name", updated.getName());
        assertEquals("4400", updated.getAddress().getStreetNumber());
        assertEquals("Mississauga", updated.getAddress().getCity());
        assertEquals("ON", updated.getAddress().getProvince());

        verify(customerRepo).findById(5L);
        verify(customerRepo).save(existing);
    }

    @Test
    void testUpdateCustomer_CreatesWhenMissing_Company() {
        when(customerRepo.findById(9L)).thenReturn(Optional.empty());
        when(customerRepo.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Address address = new Address();
        address.setPostalCode("M5H 1A1");
        address.setCity("Toronto");
        address.setStreetNumber("1 King St W");
        address.setProvince("ON");

        CustomerDto dto = new CustomerDto();
        dto.setCustomerType("company");
        dto.setName("ACME Inc");
        dto.setAddress(address);

        Customer saved = service.updateCustomer(dto, 9L);

        assertThat(saved).isInstanceOf(Company.class);
        assertEquals("ACME Inc", saved.getName());
        assertEquals("Toronto", saved.getAddress().getCity());
        verify(customerRepo).save(any(Customer.class));
    }

    @Test
    void testDeleteCustomer_delegates() {
        service.deleteCustomer(7L);
        verify(customerRepo).deleteById(7L);
    }

    @Test
    void testGetAllCustomers_delegates() {
        Person c1 = new Person();
        Company c2 = new Company();
        when(customerRepo.findAll()).thenReturn(List.of(c1, c2));

        List<Customer> result = service.getAllCustomers();

        assertThat(result).containsExactly(c1, c2);
        verify(customerRepo).findAll();
    }

    @Test
    void testSetAccount_linksAccountAndSavesCustomer() {
        Person customer = new Person();
        customer.setAccounts(new ArrayList<>());

        CheckingAccount account = new CheckingAccount();

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
        when(accountRepo.findById(2L)).thenReturn(Optional.of(account));
        when(customerRepo.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        service.setAccount(1L, 2L);

        verify(customerRepo).save(captor.capture());
        Customer saved = captor.getValue();

        assertThat(saved.getAccounts()).contains(account);
        assertThat(account.getCustomer()).isSameAs(saved);
    }

    @Test
    void testRetrieveEmployeeById_throwsWhenMissing() {
        when(customerRepo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.retrieveEmployeeById(99L))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("Customer not found");
    }
}
