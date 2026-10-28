package com.xacc.adnotatio.Customer.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumericOnlyValidator.class)
public @interface NumericOnly {
    String message() default "Input hanya boleh berisi angka";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}