package com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.PedidoResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;

public interface IPedidoResponseMapper {

    PedidoResponseDTO toPedidoDTO(PedidoModel pedidoModel);
}
