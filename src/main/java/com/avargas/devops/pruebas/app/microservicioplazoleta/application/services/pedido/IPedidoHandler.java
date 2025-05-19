package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PedidoRequestDTO;

public interface IPedidoHandler {

    void crearPedidos(PedidoRequestDTO dto);

}
