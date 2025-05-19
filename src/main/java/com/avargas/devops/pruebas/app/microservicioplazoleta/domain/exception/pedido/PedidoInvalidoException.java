package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.pedido;

public class PedidoInvalidoException extends RuntimeException{

    public PedidoInvalidoException(String message) {
        super(message);
    }

}
