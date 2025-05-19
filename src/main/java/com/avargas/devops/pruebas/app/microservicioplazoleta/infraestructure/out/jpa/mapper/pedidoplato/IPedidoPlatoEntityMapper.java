package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.pedidoplato;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoPlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoPlatoEntity;

import java.util.List;

public interface IPedidoPlatoEntityMapper {

    PedidoPlatoEntity toEntity(PedidoPlatoModel model);

    PedidoPlatoModel toModel(PedidoPlatoEntity entity);

    List<PedidoPlatoModel> toModelList(List<PedidoPlatoEntity> entities);
}
