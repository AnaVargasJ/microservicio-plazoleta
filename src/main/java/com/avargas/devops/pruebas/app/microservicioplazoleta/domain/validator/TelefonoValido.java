package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TelefonoValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TelefonoValido {
    String message() default "El teléfono es inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

