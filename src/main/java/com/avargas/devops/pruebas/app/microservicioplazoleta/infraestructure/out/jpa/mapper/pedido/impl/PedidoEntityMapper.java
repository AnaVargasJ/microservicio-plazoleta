package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.pedido.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoPlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoPlatoEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.pedido.IPedidoEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.restaurantes.IRestauranteEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PedidoEntityMapper implements IPedidoEntityMapper {


    private final IRestauranteEntityMapper restauranteMapper;
    @Override
    public PedidoEntity toEntity(PedidoModel model) {
        if (model == null) {
            return null;
        }

        return PedidoEntity.builder()
                .id(model.getId())
                .estado(model.getEstado())
                .fecha(model.getFecha())
                .idChef(model.getIdChef())
                .idCliente(model.getIdCliente())
                .restauranteEntity(
                        model.getRestauranteModel() != null
                                ? restauranteMapper.toRestauranteEntity(model.getRestauranteModel())
                                : null
                )
                .build();
    }


    @Override
    public PedidoModel toModel(PedidoEntity entity) {
        if (entity == null) {
            return null;
        }

        return PedidoModel.builder()
                .id(entity.getId())
                .estado(entity.getEstado())
                .fecha(entity.getFecha())
                .idChef(entity.getIdChef())
                .idCliente(entity.getIdCliente())
                .restauranteModel(
                        entity.getRestauranteEntity() != null
                                ? restauranteMapper.toRestauranteModel(entity.getRestauranteEntity())
                                : null
                )
                .build();
    }

    @Override
    public List<PedidoModel> toModelList(List<PedidoEntity> entities) {
        if (entities == null) return new ArrayList<>();

        return entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

}
