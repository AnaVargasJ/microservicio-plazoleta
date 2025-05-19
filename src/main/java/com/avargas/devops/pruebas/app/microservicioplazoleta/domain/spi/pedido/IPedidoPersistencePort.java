package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;

public interface IPedidoPersistencePort {

    void guardarPedido(PedidoModel pedidoModel);
    Boolean existePedidoEnProceso(Long idCliente);
}
