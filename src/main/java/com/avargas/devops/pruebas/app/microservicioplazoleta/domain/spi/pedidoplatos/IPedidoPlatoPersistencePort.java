package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedidoplatos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoPlatoModel;

import java.util.List;

public interface IPedidoPlatoPersistencePort {

    void guardarPlatosDePedido(List<PedidoPlatoModel> platos, Long idPedido);
}
