package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.adapter.pedidoplato;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoPlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedidoplatos.IPedidoPlatoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoPlatoEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoPlatoIdEmbeddable;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PlatoEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.pedidoplato.impl.PedidoPlatoEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.pedido.PedidoEntityRepository;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.pedidoplato.PedidoPlatoRepository;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.platos.PlatoRepository;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.MensajeRepositories;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PedidoPlatoJpaAdapter implements IPedidoPlatoPersistencePort {

    private final PedidoPlatoRepository pedidoPlatoRepository;
    private final PedidoEntityRepository pedidoRepository;
    private final PlatoRepository platoRepository;
    private final PedidoPlatoEntityMapper pedidoPlatoEntityMapper;
    @Override
    public void guardarPlatosDePedido(List<PedidoPlatoModel> platos, Long idPedido) {

        PedidoEntity pedidoEntity = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new EntityNotFoundException(MensajeRepositories.PEDIDO_NO_ENCONTRADO + idPedido));

        List<PedidoPlatoEntity> entidades = platos.stream().map(model -> {
            PlatoEntity platoEntity = platoRepository.findById(model.getPlatoModel().getId())
                    .orElseThrow(() -> new EntityNotFoundException(MensajeRepositories.PLATO_NO_ENCONTRADO + model.getPlatoModel().getId()));

            PedidoPlatoEntity entity = pedidoPlatoEntityMapper.toEntity(model);

            entity.setPedidoEntity(pedidoEntity);
            entity.setPlatoEntity(platoEntity);
            entity.setId(new PedidoPlatoIdEmbeddable(pedidoEntity.getId(), platoEntity.getId()));

            return entity;
        }).toList();

        pedidoPlatoRepository.saveAll(entidades);
    }
}
