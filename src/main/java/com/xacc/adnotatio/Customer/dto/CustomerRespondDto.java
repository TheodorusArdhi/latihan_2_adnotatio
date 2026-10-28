package com.xacc.adnotatio.Customer.dto;

public record CustomerRespondDto(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber,
        String email
) {
}
