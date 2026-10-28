package com.xacc.adnotatio.Cart.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CartRespondDto(
        CartCustomerDto cartCustomerDto,
        CartProductDto cartProductDto
) {
    public record CartCustomerDto(
            Long id,
            String firstName,
            String lastName
    ) {}

    public record CartProductDto(
            Integer id,
            String name,
            BigDecimal price,
            Integer quantity,
            LocalDateTime addedDate
    ) {}
}