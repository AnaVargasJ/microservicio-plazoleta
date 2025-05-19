package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.pedidoplato.impl;


import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoPlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoPlatoEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoPlatoIdEmbeddable;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.pedido.IPedidoEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.pedido.impl.PedidoEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.pedidoplato.IPedidoPlatoEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.platos.impl.PlatoEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoPlatoEntityMapper implements IPedidoPlatoEntityMapper {

    private final PlatoEntityMapper platoEntityMapper;
    private final PedidoEntityMapper pedidoEntityMapper;


    @Override
    public PedidoPlatoEntity toEntity(PedidoPlatoModel model) {
        if (model == null) return null;

        Long pedidoId = model.getPedidoModel().getId();
        Long platoId = model.getPlatoModel().getId();

        return PedidoPlatoEntity.builder()
                .id(new PedidoPlatoIdEmbeddable(pedidoId, platoId))
                .pedidoEntity(pedidoEntityMapper.toEntity(model.getPedidoModel()))
                .platoEntity(platoEntityMapper.toPlatoEntity(model.getPlatoModel()))
                .cantidad(model.getCantidad())
                .build();
    }

    @Override
    public PedidoPlatoModel toModel(PedidoPlatoEntity entity) {
        if (entity == null) return null;

        return PedidoPlatoModel.builder()
                .pedidoModel(pedidoEntityMapper.toModel(entity.getPedidoEntity()))
                .platoModel(platoEntityMapper.toPlatoModel(entity.getPlatoEntity()))
                .cantidad(entity.getCantidad())
                .build();
    }
}
