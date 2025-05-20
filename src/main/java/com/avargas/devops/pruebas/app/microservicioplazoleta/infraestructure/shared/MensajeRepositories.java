package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared;

public class MensajeRepositories {

    private MensajeRepositories() {
    }

    public static final String PEDIDO_NO_ENCONTRADO = "Pedido no encontrado con ID: ";

    public static final String PLATO_NO_ENCONTRADO = "Plato no encontrado con ID: ";

    public static final String ESTADO_PEDIDO = "El estado de pedido es inválido.";
    public static final String EMPLEADO_ASOCIADO = "El empleado no tiene restaurante asociado.";

    public static String NO_EXISTE_PEDIDO_RESTAURANTE = "No existen pedidos en el restaurante ";
}
