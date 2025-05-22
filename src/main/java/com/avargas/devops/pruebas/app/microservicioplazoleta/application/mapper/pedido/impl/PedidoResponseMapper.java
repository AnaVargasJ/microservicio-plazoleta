package com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.pedido.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.PedidoResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.pedido.IPedidoResponseMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResponseMapper implements IPedidoResponseMapper {
    @Override
    public PedidoResponseDTO toPedidoDTO(PedidoModel pedidoModel) {
        return PedidoResponseDTO.builder()
                .idPedido(pedidoModel.getId())
                .fecha(formatearFecha(pedidoModel.getFecha()))
                .estado(pedidoModel.getEstado())
                .idCliente(pedidoModel.getIdCliente())
                .idChef(pedidoModel.getIdChef())
                .build();
    }

    @Override
    public List<PedidoResponseDTO> toModelList(List<PedidoModel> pedidoModelList) {
        return pedidoModelList.stream()
                .map(this::toPedidoDTO)
                .collect(Collectors.toList());
    }


    private String formatearFecha( LocalDateTime fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fecha.format(formatter);
        return fechaFormateada;
    }
}
