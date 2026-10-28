package com.xacc.adnotatio.Cart.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CartCustomerRespondDto(
        Integer id,
        String name,
        BigDecimal price,
        Integer quantity,
        LocalDateTime addedDate
) {
}
