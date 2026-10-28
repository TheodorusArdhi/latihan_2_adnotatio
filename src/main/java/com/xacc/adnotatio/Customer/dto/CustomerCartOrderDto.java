package com.xacc.adnotatio.Customer.dto;

import com.xacc.adnotatio.Order.Order;
import com.xacc.adnotatio.Product.Product;

import java.math.BigDecimal;

public record CustomerCartOrderDto(
        Long orderId,
        BigDecimal price,
        Integer quantity,
        Order order,
        Product product
) {
}
