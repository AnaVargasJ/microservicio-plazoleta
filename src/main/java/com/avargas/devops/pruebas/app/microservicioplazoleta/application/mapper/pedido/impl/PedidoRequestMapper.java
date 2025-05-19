package com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.pedido.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PedidoRequestDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoCantidadDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.pedido.IPedidoRequestMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoPlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoRequestMapper implements IPedidoRequestMapper {
    @Override
    public PedidoModel toModel(PedidoRequestDTO dto) {
        return PedidoModel.builder()
                .idCliente(dto.getIdCliente())
                .restauranteModel(RestauranteModel.builder()
                        .id(dto.getIdRestaurante())
                        .build())
                .platos(toPedidoPlatoModelList(dto.getPlatos()))
                .build();
    }

    private List<PedidoPlatoModel> toPedidoPlatoModelList(List<PlatoCantidadDTO> platos) {
        return platos.stream().map(this::toPedidoPlatoModel).collect(Collectors.toList());
    }

    private PedidoPlatoModel toPedidoPlatoModel(PlatoCantidadDTO dto) {
        return PedidoPlatoModel.builder()
                .platoModel(PlatoModel.builder().id(dto.getIdPlato()).build())
                .cantidad(dto.getCantidad())
                .build();
    }
}
