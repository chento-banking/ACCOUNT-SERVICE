package com.chento.account.service;

import com.chento.account.dto.CustomerDTO;
import com.chento.account.dto.CustomerDetailDTO;
import com.chento.account.entity.Customer;

public interface CustomerService {
    Customer save(CustomerDTO customerDTO);
    Customer getById(Long id);
    CustomerDetailDTO getCustomerDetail(String correlationId,Long customerId);
    CustomerDetailDTO getCustomerDefaultFallback(Long customerId);

}
