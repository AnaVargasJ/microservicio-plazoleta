package com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PedidoRequestDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;

public interface IPedidoRequestMapper {

    PedidoModel toModel(PedidoRequestDTO dto);

}
