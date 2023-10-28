package com.example.serviceprovider.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileSizeValidator.class)
public @interface FileSize {
    String message() default "File size exceeds the allowed limit.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    long max() default Long.MAX_VALUE;
}

