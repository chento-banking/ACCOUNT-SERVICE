package com.chento.account.controller;

import com.chento.account.dto.CustomerDTO;
import com.chento.account.dto.CustomerDetailDTO;
import com.chento.account.entity.Customer;
import com.chento.account.mapper.CustomerMapper;
import com.chento.account.service.CardFeignClient;
import com.chento.account.service.CustomerService;
import com.chento.account.service.LoanFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/customers")
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = customerService.save(customerDTO);
        return ResponseEntity.ok(customer);
    }

//    @CircuitBreaker(name = "customerDetailSupport")
    @Retry(name = "retryForCustomerDetail",fallbackMethod = "getCustomerDefaultFallback")
    @GetMapping("customer-detail/{customerId}")
    public ResponseEntity<CustomerDetailDTO> getCustomerDetail(
            @RequestHeader("chentobank-correlation-id") String correlationId,
            @PathVariable Long customerId){
            log.debug("correlation-id found: {}",correlationId);
        return ResponseEntity.ok(customerService.getCustomerDetail(correlationId,customerId));
    }

    public ResponseEntity<CustomerDetailDTO> getCustomerDefaultFallback(
            @RequestHeader("chentobank-corelation-id") String correlationId,
            @PathVariable Long customerId,Throwable e){

        log.debug("correlation-id found in fallback: {}",correlationId);

        return ResponseEntity.ok(customerService.getCustomerDefaultFallback(customerId));
    }





    @GetMapping("/sayHello")
    @RateLimiter(name = "sayHelloLimiter",fallbackMethod = "sayHi")
    public String sayHello(){
        return "Hello, welcome to ChentoBank";
    }

    public String sayHi(Throwable t){
        return "Hi, welcome to ChentoBank";
    }


}
