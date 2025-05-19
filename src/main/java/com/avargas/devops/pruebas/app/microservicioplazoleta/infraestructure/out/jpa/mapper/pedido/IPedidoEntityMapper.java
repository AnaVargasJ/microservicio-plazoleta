package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoEntity;



public interface IPedidoEntityMapper {
    PedidoEntity toEntity(PedidoModel pedidoModel);

    PedidoModel toModel(PedidoEntity pedidoEntity);


}