package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.pedido.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PedidoRequestDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.pedido.IPedidoRequestMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.pedido.IPedidoHandler;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.pedido.IPedidoServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoHandler implements IPedidoHandler {

    private final IPedidoRequestMapper pedidoRequestMapper;
    private final IPedidoServicePort pedidoServicePort;
    @Override
    public void crearPedidos(PedidoRequestDTO dto) {
        PedidoModel pedidoModel = pedidoRequestMapper.toModel(dto);
        pedidoServicePort.crearPedido(pedidoModel);
    }
}
