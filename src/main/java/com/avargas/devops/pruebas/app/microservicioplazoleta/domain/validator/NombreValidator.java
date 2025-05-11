package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NombreValidator implements ConstraintValidator<NombreValido, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El nombre es obligatorio").addConstraintViolation();
            return false;
        }
        if (value.trim().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El nombre no puede estar vacío").addConstraintViolation();
            return false;
        }
        if (value.matches("^\\d+$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El nombre no puede ser solo números").addConstraintViolation();
            return false;
        }
        return true;
    }
}

