package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.pedidoplato;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoPlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoPlatoEntity;

public interface IPedidoPlatoEntityMapper {

    PedidoPlatoEntity toEntity(PedidoPlatoModel model);

    PedidoPlatoModel toModel(PedidoPlatoEntity entity);
}
