package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;

public interface IPedidoServicePort {

    void crearPedido(PedidoModel pedidoModel);
}
