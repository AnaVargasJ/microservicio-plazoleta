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
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security.jwt.TokenJwtConfig;
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

    private static final String AUTHORIZATION_HEADER = "Authorization";
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
        String token = request.getHeader(AUTHORIZATION_HEADER);
        pedidoServicePort.asignarPedido(token,idPedido,estado, idUsuario, null);
    }

    @Override
    public void asignarPedidoPin(Long idPedido, String estado, Long idUsuario, String pin) {
        pedidoServicePort.asignarPedido(null,idPedido,estado, idUsuario, pin);
    }
}
