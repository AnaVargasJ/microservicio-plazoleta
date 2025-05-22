package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PedidoRequestDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.PedidoResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security.model.UsuarioAutenticado;
import jakarta.servlet.http.HttpServletRequest;

public interface IPedidoHandler {

    void crearPedidos(PedidoRequestDTO dto);

    PageResponseDTO<PedidoResponseDTO> obtenerListaPedidosPorEstado(String estado, Long idRestaurante, int page, int size, Long idUsuario);

    void asignarPedido(HttpServletRequest request, Long idPedido, String estado, Long idUsuario, String correo);
    void asignarPedidoPin( HttpServletRequest request, Long idPedido, String estado, Long idUsuario, String pin, String correo);
    void cancelarPedido( Long idPedido, Long idUsuario, String correoCliente, String token);

}
