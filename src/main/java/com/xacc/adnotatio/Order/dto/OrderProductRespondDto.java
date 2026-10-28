package com.xacc.adnotatio.Order.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderProductRespondDto(
        Long id,
        Long customerId,
        String customerName,
        LocalDate orderDate,
        BigDecimal deliveryFee,
        BigDecimal totalAmount,
        List<OrderItemRespondDto> orderItems
) {

}
