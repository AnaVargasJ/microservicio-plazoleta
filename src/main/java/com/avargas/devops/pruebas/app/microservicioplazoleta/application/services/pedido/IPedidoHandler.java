package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PedidoRequestDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.PedidoResponseDTO;

public interface IPedidoHandler {

    void crearPedidos(PedidoRequestDTO dto);

    PageResponseDTO<PedidoResponseDTO> obtenerListaPedidosPorEstado(String estado, Long idRestaurante, int page, int size);

}
