package com.xacc.adnotatio.Product.dto;

import java.math.BigDecimal;

public record ProductRespondDto(
        Integer id,
        String name,
        BigDecimal price,
        Integer stock,
        Boolean deactivated
) {

}
