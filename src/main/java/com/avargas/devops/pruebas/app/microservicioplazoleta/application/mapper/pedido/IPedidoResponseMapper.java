package com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.PedidoResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;

import java.util.List;

public interface IPedidoResponseMapper {

    PedidoResponseDTO toPedidoDTO(PedidoModel pedidoModel);

    List<PedidoResponseDTO> toModelList(List<PedidoModel> pedidoModelList);

}
