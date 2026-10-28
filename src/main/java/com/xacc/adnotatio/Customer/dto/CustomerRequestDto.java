package com.xacc.adnotatio.Customer.dto;

import com.xacc.adnotatio.Customer.validator.NumericOnly;
import jakarta.persistence.EmbeddedId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.NumberFormat;

public record CustomerRequestDto(
        @NotBlank(message = "Harus ada isinya untuk firstName!")
        @NotNull
        @Size(max = 50)
        String firstName,

        @NotBlank(message = "Harus ada isinya untuk lastName!")
        @NotNull
        @Size(max = 50)
        String lastName,

        @NotBlank(message = "Harus ada isinya untuk phoneNumber!")
        @Size(min = 10, max = 12)
        @NumericOnly(message = "Nomor telepon hanya boleh berisi angka!")
        String phoneNumber,

        @NotBlank(message = "Harus ada isinya untuk email!")
        @Email(message = "harus format email wok")
        String email
) {
}
