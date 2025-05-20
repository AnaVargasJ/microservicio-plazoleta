package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MensajeError {

    EMPLEADO_NO_ASOCIADO("El empleado no tiene restaurante asociado.");
    private final String message;
}
