package com.fdmgroup.management.AccountCustMgmt;

import com.fdmgroup.management.AccountCustMgmt.controller.CustomerController;
//import com.fdmgroup.management.AccountCustMgmt.model.Account;
import com.fdmgroup.management.AccountCustMgmt.model.Address;
import com.fdmgroup.management.AccountCustMgmt.model.Customer;
import com.fdmgroup.management.AccountCustMgmt.model.CustomerDto;
import com.fdmgroup.management.AccountCustMgmt.services.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AccountCustMgmtApplication implements CommandLineRunner {

    ApplicationContext ctx;

    public AccountCustMgmtApplication(ApplicationContext ctx) {
        super();
        this.ctx = ctx;
    }

    private final String CONTACT_BASE_URL = "https://geocoder.ca/";

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient contactWebClient(WebClient.Builder builder) {
        return builder.baseUrl(CONTACT_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(AccountCustMgmtApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        CustomerController controller = ctx.getBean(CustomerController.class);
//        controller.createCustomer(aaron);
//        Thread.sleep(600);
//        controller.createCustomer("Aaron", "L5C 4J7", "4400", "person");
//        Thread.sleep(600);
//        controller.createCustomer("Bad", "L5C 4J8", "10", "person");
//        Thread.sleep(600);
//        controller.createCustomer("Fin", "L5C 4J9", "50", "person");
//        System.out.println(controller.getAllCustomers());
//        Address address1 = new Address("5000", "L6C 4J7", "Mississauga", "ON");
//        List<Account> accounts = new ArrayList<>();
//        CustomerDto update = new CustomerDto("Aaron",address1, "doctor");
//        controller.updateCustomer(1, update);
//        System.out.println(controller.findCustomerById(1));

    }
}
