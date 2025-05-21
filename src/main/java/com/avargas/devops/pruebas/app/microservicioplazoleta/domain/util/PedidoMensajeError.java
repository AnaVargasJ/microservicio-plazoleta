package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.util;


public class PedidoMensajeError {

    private PedidoMensajeError() {
    }

    public static final String PEDIDO_EN_PROCESO = "El cliente ya tiene un pedido en proceso.";

    public static final String PLATOS_DISTINTO_RESTAURANTE = "Todos los platos deben pertenecer al mismo restaurante.";

    public static final String NO_EXISTE_PLATOS = "No existe el plado con el id ";
    public static final String NO_EXISTE_ESTADOS = "Solo se pueden asignar pedidos en estado ";
    public static final String NO_EXISTE_EMPLEADO = "El restaurante esta asociado al empleado ";
    public static final String PEDIDO_DISTINTO_LISTO = "Solo los pedidos en estado LISTO pueden ser entregados.";
    public static final String PIN_INCORRECTO = "El PIN de seguridad es incorrecto.";
    public static final String PEDIDO_ENTREGADO = "No se puede modificar un pedido ya entregado.";



}
