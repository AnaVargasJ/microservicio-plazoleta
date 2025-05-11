package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NitValidator implements ConstraintValidator<NitValido, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El NIT es obligatorio").addConstraintViolation();
            return false;
        }
        if (value.trim().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El NIT no puede estar vacío").addConstraintViolation();
            return false;
        }
        if (!value.matches("^\\d+$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El NIT debe contener solo números").addConstraintViolation();
            return false;
        }
        return true;
    }
}
