package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.pedido.IPedidoServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.pedido.PedidoInvalidoException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.EstadoPedido;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoPlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedido.IPedidoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.platos.PlatoPersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static com.avargas.devops.pruebas.app.microservicioplazoleta.domain.util.PedidoMensajeError.PEDIDO_EN_PROCESO;
import static com.avargas.devops.pruebas.app.microservicioplazoleta.domain.util.PedidoMensajeError.PLATOS_DISTINTO_RESTAURANTE;

@RequiredArgsConstructor
public class PedidoUseCase implements IPedidoServicePort {

    private final IPedidoPersistencePort persistencePort;
    private final PlatoPersistencePort platoPersistencePort;

    @Override
    public void crearPedido(PedidoModel pedidoModel) {
        Long idCliente = pedidoModel.getIdCliente();
        Long idRestaurante = pedidoModel.getRestauranteModel().getId();

        if (persistencePort.existePedidoEnProceso(idCliente))
            throw new PedidoInvalidoException(PEDIDO_EN_PROCESO);

        for (PedidoPlatoModel platoPedido : pedidoModel.getPlatos()) {
            PlatoModel plato = platoPersistencePort.getPlatoModelById(platoPedido.getPlatoModel().getId());
            if (!plato.getRestauranteModel().getId().equals(idRestaurante))
                throw new PedidoInvalidoException(PLATOS_DISTINTO_RESTAURANTE);

            pedidoModel.setEstado(String.valueOf(EstadoPedido.PENDIENTE));
            pedidoModel.setFecha(LocalDateTime.now());



            persistencePort.guardarPedido(pedidoModel);
        }
    }
}
