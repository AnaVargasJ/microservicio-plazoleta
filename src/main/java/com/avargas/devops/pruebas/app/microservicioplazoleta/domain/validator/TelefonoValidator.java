package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefonoValidator implements ConstraintValidator<TelefonoValido, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El teléfono es obligatorio").addConstraintViolation();
            return false;
        }
        if (value.trim().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El teléfono no puede estar vacío").addConstraintViolation();
            return false;
        }
        if (!value.matches("^\\+?[0-9]{1,13}$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Formato de teléfono inválido: debe ser numérico, máximo 13 caracteres y puede iniciar con +").addConstraintViolation();
            return false;
        }
        return true;
    }
}
