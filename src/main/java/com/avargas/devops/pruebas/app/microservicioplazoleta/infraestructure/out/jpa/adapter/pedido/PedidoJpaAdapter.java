package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.adapter.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PageModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedido.IPedidoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.pedido.IPedidoEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.pedido.PedidoEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class PedidoJpaAdapter implements IPedidoPersistencePort {

    private final PedidoEntityRepository pedidoRepository;
    private final IPedidoEntityMapper entityMapper;
    @Override
    public PedidoModel guardarPedido(PedidoModel pedidoModel) {
        PedidoEntity entity = entityMapper.toEntity(pedidoModel);
        entity = pedidoRepository.save(entity);
        PedidoModel model = entityMapper.toModel(entity);
        return model;
    }

    @Override
    public Boolean existePedidoEnProceso(Long idCliente) {
        List<String> estadosEnProceso = List.of("PENDIENTE", "EN_PREPARACION", "LISTO");
        return pedidoRepository.existsByIdClienteAndEstadoIn(idCliente, estadosEnProceso);
    }

    @Override
    public PageModel<PedidoModel> obtenerPedidosPorEstadoYRestaurante(String estado, Long idRestaurante, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PedidoEntity> pedidosPaginados = pedidoRepository.findByEstadoAndRestauranteId(estado,idRestaurante, pageable);
        List<PedidoModel> modelos = entityMapper.toModelList(pedidosPaginados.getContent());

        return PageModel.<PedidoModel>builder()
                .content(modelos)
                .currentPage(pedidosPaginados.getNumber())
                .pageSize(pedidosPaginados.getSize())
                .totalElements(pedidosPaginados.getTotalElements())
                .totalPages(pedidosPaginados.getTotalPages())
                .hasNext(pedidosPaginados.hasNext())
                .hasPrevious(pedidosPaginados.hasPrevious())
                .build();
    }
}
