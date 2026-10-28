package com.xacc.adnotatio.Product.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequestDto(
        @NotNull
        String name,

        @NotNull
        @Min(0)
        @Max(999999999)
        BigDecimal price,

        @NotNull
        @Min(0)
        Integer stock,

        @NotNull
        Boolean deactivated
) {
}
