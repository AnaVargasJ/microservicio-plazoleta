package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;

public interface IPedidoPersistencePort {

    PedidoModel guardarPedido(PedidoModel pedidoModel);
    Boolean existePedidoEnProceso(Long idCliente);
}
