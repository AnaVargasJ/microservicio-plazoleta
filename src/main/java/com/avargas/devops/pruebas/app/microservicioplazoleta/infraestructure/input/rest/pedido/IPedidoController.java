package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.pedido;


import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PedidoRequestDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security.model.UsuarioAutenticado;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface IPedidoController {

    ResponseEntity<?> crearPedido(HttpServletRequest request, PedidoRequestDTO pedidoRequestDTO);
    ResponseEntity<?> obtenerListaPedidosPorEstado(String estado, Long idRestaurante, int page, int size, UsuarioAutenticado usuarioAutenticado);

    ResponseEntity<?> asignarPedido(HttpServletRequest request,Long idPedido, String estado, UsuarioAutenticado usuarioAutenticado);
    ResponseEntity<?> entregarPedido(HttpServletRequest request,Long idPedido, String estado,String pin, UsuarioAutenticado usuarioAutenticado);
    ResponseEntity<?> cancelarPedido(HttpServletRequest request,Long idPedido, UsuarioAutenticado usuarioAutenticado);

    ResponseEntity<?> filtrarPedidosPorRestaurante(Long idRestaurant);

}
