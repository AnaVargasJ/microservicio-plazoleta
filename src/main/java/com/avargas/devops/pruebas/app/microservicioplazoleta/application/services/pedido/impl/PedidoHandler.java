package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.pedido.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PedidoRequestDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.PedidoResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.pedido.IPedidoRequestMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.pedido.impl.IPagePedidoResponseMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.pedido.IPedidoHandler;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.pedido.INotificacionServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.pedido.IPedidoServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.EstadoPedido;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoHandler implements IPedidoHandler {

    private final IPedidoRequestMapper pedidoRequestMapper;
    private final IPedidoServicePort pedidoServicePort;
    private final IPagePedidoResponseMapper iPageResponseMapper;
    private final INotificacionServicePort notificacionServicePort;
    @Override
    public void crearPedidos(PedidoRequestDTO dto) {
        PedidoModel pedidoModel = pedidoRequestMapper.toModel(dto);
        pedidoServicePort.crearPedido(pedidoModel);
    }

    @Override
    public PageResponseDTO<PedidoResponseDTO> obtenerListaPedidosPorEstado(String estado, Long idRestaurante, int page, int size,  Long idUsuario) {
        return iPageResponseMapper.toResponse(pedidoServicePort.obtenerPedidosPorEstadoYRestaurante(estado, idRestaurante, page, size, idUsuario));

    }

    @Override
    public void asignarPedido(HttpServletRequest request, Long idPedido, String estado, Long idUsuario) {
        if (String.valueOf(EstadoPedido.LISTO).equals(estado)){
            Boolean notificar = notificacionServicePort.notificarUsuario(request,idUsuario,estado);
        }
        pedidoServicePort.asignarPedido(idPedido,estado, idUsuario);
    }
}
