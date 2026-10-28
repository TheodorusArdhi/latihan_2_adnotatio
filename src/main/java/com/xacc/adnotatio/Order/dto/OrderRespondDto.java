package com.xacc.adnotatio.Order.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderRespondDto(
        Long id,
        Long customerId,
        String customerName,
        LocalDate orderDate,
        BigDecimal deliveryFee,
        BigDecimal totalAmount
) {
}
