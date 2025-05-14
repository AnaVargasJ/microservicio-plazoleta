package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.exception;

public class RolNoAutorizadoException extends RuntimeException {
    public RolNoAutorizadoException(String mensaje) {
        super(mensaje);
    }
}
