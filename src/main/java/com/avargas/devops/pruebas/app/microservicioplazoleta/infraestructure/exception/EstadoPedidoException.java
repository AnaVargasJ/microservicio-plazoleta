package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.exception;

public class EstadoPedidoException extends RuntimeException {
    public EstadoPedidoException(String mensaje) {
        super(mensaje);
    }
}
