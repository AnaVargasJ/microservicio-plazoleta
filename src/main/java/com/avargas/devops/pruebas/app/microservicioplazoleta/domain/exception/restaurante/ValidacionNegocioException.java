package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.restaurante;

public class ValidacionNegocioException extends RuntimeException{

    public ValidacionNegocioException(String message) {
        super(message);
    }
}
