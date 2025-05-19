package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PedidoEntityMapper {
    PedidoEntity toEntity(PedidoModel pedidoModel);

    PedidoModel toModel(PedidoEntity pedidoEntity);


}