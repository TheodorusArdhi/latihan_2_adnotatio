package com.xacc.adnotatio.Cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartPatchRequestDto(
        @NotNull(message = "Quantity wajib diisi")
        @Min(value = 1, message = "Quantity minimal 1")
        Integer quantity
) {
}
