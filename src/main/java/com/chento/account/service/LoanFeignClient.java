package com.chento.account.service;


import com.chento.account.dto.CardResponseDTO;
import com.chento.account.dto.LoanResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "loan" )
public interface LoanFeignClient {

    @GetMapping(value = "/api/loans/{customerId}",consumes = "application/json")
    List<LoanResponseDTO> getLoanInfo(
            @RequestHeader("chentobank-corelation-id") String correlationId,
            @PathVariable Long customerId
    );

}
