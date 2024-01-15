package com.chento.account.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CustomerDTO(
         Long customerId,
         String name,
         String email,
         String mobileNumber,
         LocalDate createdAt
) { }
