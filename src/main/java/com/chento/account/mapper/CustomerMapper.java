package com.chento.account.mapper;


import com.chento.account.dto.CustomerDTO;
import com.chento.account.entity.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CustomerMapper {
    public Customer toCustomer(CustomerDTO model){
        Customer customer=new Customer();
        customer.setName(model.name());
        customer.setEmail(model.email());
        customer.setCreatedAt(LocalDate.now());
        customer.setMobileNumber(model.mobileNumber());
        return customer;
    }
    public CustomerDTO toCustomerDTO(Customer model){
        return new CustomerDTO(
                model.getCustomerId(),
                model.getName(),
                model.getEmail(),
                model.getMobileNumber(),
                model.getCreatedAt()
        );
    }
}
