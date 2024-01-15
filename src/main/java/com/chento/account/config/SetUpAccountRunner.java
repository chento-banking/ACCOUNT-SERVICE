package com.chento.account.config;

import com.chento.account.entity.Account;
import com.chento.account.entity.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SetUpAccountRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {

        Customer customer = new Customer();
        customer.setCreatedAt(LocalDate.now());
        customer.setEmail("chento@gmail.com");
        customer.setMobileNumber("099384617");
        customer.setName("Chento");

        Account account =new Account();
        account.setAccountNumber(1L);
        account.setAccountType("Saving");
        account.setBranchAddress("Phnom Penh");
        account.setCreateAt(LocalDate.now());
        account.setCustomer(customer);

    }
}
