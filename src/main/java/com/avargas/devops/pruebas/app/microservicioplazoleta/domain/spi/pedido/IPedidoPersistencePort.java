package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PageModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;

import java.util.List;

public interface IPedidoPersistencePort {

    PedidoModel guardarPedido(PedidoModel pedidoModel);
    Boolean existePedidoEnProceso(Long idCliente);

    PageModel<PedidoModel> obtenerPedidosPorEstadoYRestaurante (String estado, Long idRestaurante,int page, int size, Long idUsuario) ;

    void asignarPedido(Long idPedido, Long idUsuario, String estado);
    void asignarPinSeguridad(Long idPedido, String estado, String pinSeguridad);

    PedidoModel buscarPedidoPorId(Long idPedido);
   List<PedidoModel> buscarPedidosPorIdRestaurante(Long idRestaurante);
}
