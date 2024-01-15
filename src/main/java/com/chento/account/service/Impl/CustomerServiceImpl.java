package com.chento.account.service.Impl;

import com.chento.account.dto.CardResponseDTO;
import com.chento.account.dto.CustomerDTO;
import com.chento.account.dto.CustomerDetailDTO;
import com.chento.account.dto.LoanResponseDTO;
import com.chento.account.entity.Customer;
import com.chento.account.mapper.CustomerMapper;
import com.chento.account.respository.CustomerRepository;
import com.chento.account.service.CardFeignClient;
import com.chento.account.service.CustomerService;
import com.chento.account.service.LoanFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private  final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CardFeignClient cardFeignClient;
    private final LoanFeignClient loanFeignClient;

    @Override
    public Customer save(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toCustomer(customerDTO);
        return customerRepository.save(customer);
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public CustomerDetailDTO getCustomerDetail(String correlationId,Long customerId) {

        CustomerDetailDTO  customerDetailDTO =new CustomerDetailDTO();
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new RuntimeException("Customer not found")
        );
        CustomerDTO customerDTO = customerMapper.toCustomerDTO(customer);

//        List<LoanResponseDTO> loanInfo =new ArrayList<>();
//        List<CardResponseDTO> cardInfo = new ArrayList<>();
        List<LoanResponseDTO> loanInfo = loanFeignClient.getLoanInfo(correlationId,customerId);
        List<CardResponseDTO> cardInfo = cardFeignClient.getCardInfo(correlationId,customerId);
//        try{
//            cardInfo = cardFeignClient.getCardInfo(customerId);
//        }catch (FeignException exception){
//            System.out.println("feign client error cardInfo");
//        }
//
//        try{
//            loanInfo = loanFeignClient.getLoanInfo(customerId);
//        }catch (FeignException exception){
//            System.out.println("feign client error LoanInfo");
//        }


        customerDetailDTO.setCustomer(customerDTO);
        customerDetailDTO.setCards(cardInfo);
        customerDetailDTO.setLoans(loanInfo);

        return customerDetailDTO;
    }

    @Override
    public CustomerDetailDTO getCustomerDefaultFallback(Long customerId) {

        CustomerDetailDTO  customerDetailDTO =new CustomerDetailDTO();
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new RuntimeException("Customer not found")
        );

        CustomerDTO customerDTO = customerMapper.toCustomerDTO(customer);
        customerDetailDTO.setCustomer(customerDTO);

        return customerDetailDTO;
    }
}
