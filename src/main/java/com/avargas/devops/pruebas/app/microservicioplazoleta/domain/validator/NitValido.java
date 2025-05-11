package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NitValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NitValido {
    String message() default "El NIT es inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
