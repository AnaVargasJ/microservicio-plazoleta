package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PageModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;

public interface IPedidoPersistencePort {

    PedidoModel guardarPedido(PedidoModel pedidoModel);
    Boolean existePedidoEnProceso(Long idCliente);

    PageModel<PedidoModel> obtenerPedidosPorEstadoYRestaurante (String estado, Long idRestaurante,int page, int size, Long idUsuario) ;

    void asignarPedido(Long idPedido, Long idUsuario, String estado);

    PedidoModel buscarPedidoPorId(Long idPedido);
}
