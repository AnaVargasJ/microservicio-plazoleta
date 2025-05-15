package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.platos;

public class PlatoInvalidoException extends RuntimeException{

    public PlatoInvalidoException(String message) {
        super(message);
    }

}
