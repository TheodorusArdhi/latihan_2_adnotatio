package com.xacc.adnotatio.Order.dto;

import java.math.BigDecimal;

public record OrderItemRespondDto(
        Integer productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subTotal
) {
}
