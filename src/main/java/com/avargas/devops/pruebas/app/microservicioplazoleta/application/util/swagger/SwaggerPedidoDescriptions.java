package com.avargas.devops.pruebas.app.microservicioplazoleta.application.util.swagger;

public class SwaggerPedidoDescriptions {

    private SwaggerPedidoDescriptions() {
    }

    public static final String PEDIDO_REQUEST =
            "Solicitud para realizar un pedido por parte de un cliente en la plazoleta. "
                    + "El pedido debe pertenecer a un único restaurante y contener una lista de platos con sus cantidades.";

    public static final String ID_CLIENTE =
            "ID del cliente que realiza el pedido.";

    public static final String ID_RESTAURANTE =
            "ID del restaurante al que pertenece el pedido.";

    public static final String PLATOS =
            "Lista de platos con su cantidad.";

    public static final String PLATO_REQUEST =
            "Datos de un plato incluido en el pedido.";

    public static final String ID_PLATO =
            "ID del plato seleccionado.";

    public static final String CANTIDAD_PLATO =
            "Cantidad solicitada del plato.";
}
