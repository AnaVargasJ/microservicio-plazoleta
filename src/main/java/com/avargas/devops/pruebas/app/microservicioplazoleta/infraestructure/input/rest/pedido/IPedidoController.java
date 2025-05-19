package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.pedido;


import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PedidoRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface IPedidoController {

    ResponseEntity<?> crearPedido(HttpServletRequest request, PedidoRequestDTO pedidoRequestDTO);
    ResponseEntity<?> obtenerListaPedidosPorEstado(String estado, Long idRestaurante, int page, int size);
}
