package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;

public interface ITrazabilidadServicePort {

    void crearTraza(String token, PedidoModel pedidoModel, String estadoNuevo, Long idEmpleado,String correoEmpleado, String correoCliente);
}
