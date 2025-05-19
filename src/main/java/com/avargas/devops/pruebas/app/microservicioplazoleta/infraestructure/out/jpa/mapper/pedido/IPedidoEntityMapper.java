package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoEntity;

import java.util.List;


public interface IPedidoEntityMapper {
    PedidoEntity toEntity(PedidoModel pedidoModel);

    PedidoModel toModel(PedidoEntity pedidoEntity);

    List<PedidoModel> toModelList(List<PedidoEntity> entities);


}