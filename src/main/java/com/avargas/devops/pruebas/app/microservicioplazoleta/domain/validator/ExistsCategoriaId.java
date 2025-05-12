package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsCategoriaIdValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsCategoriaId {
    String message() default "La categoría no existe";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


