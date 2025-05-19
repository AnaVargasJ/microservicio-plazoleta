package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.adapter.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedido.IPedidoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.pedido.PedidoEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.pedido.PedidoEntityRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PedidoJpaAdapter implements IPedidoPersistencePort {

    private final PedidoEntityRepository pedidoRepository;
    private final PedidoEntityMapper entityMapper;
    @Override
    public void guardarPedido(PedidoModel pedidoModel) {
        PedidoEntity entity = entityMapper.toEntity(pedidoModel);
        pedidoRepository.save(entity);
    }

    @Override
    public Boolean existePedidoEnProceso(Long idCliente) {
        List<String> estadosEnProceso = List.of("PENDIENTE", "EN_PREPARACION", "LISTO");
        return pedidoRepository.existsByIdClienteAndEstadoIn(idCliente, estadosEnProceso);
    }
}
