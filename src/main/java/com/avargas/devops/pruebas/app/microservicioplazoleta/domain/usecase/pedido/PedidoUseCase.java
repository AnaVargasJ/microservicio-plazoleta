package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.pedido.IPedidoServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.pedido.PedidoInvalidoException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.restaurante.RestauranteDataException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.*;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedido.IPedidoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedidoplatos.IPedidoPlatoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.platos.PlatoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.EndpointApi;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static com.avargas.devops.pruebas.app.microservicioplazoleta.domain.util.PedidoMensajeError.*;

@RequiredArgsConstructor
public class PedidoUseCase implements IPedidoServicePort {

    private final IPedidoPersistencePort persistencePort;
    private final PlatoPersistencePort platoPersistencePort;
    private final IPedidoPlatoPersistencePort pedidoPlatoPersistencePort;

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

            platoPedido.setPlatoModel(plato);
        }

        pedidoModel.setEstado(String.valueOf(EstadoPedido.PENDIENTE));
        pedidoModel.setFecha(LocalDateTime.now());

        PedidoModel pedidoGuardado = persistencePort.guardarPedido(pedidoModel);

        for (PedidoPlatoModel platoModel : pedidoModel.getPlatos()) {
            platoModel.setPedidoModel(pedidoGuardado);
        }

        pedidoPlatoPersistencePort.guardarPlatosDePedido(pedidoModel.getPlatos(), pedidoGuardado.getId());
    }

    @Override
    public PageModel<PedidoModel> obtenerPedidosPorEstadoYRestaurante(String estado, Long idRestaurante, int page, int size, Long idUsuario) {

        return persistencePort.obtenerPedidosPorEstadoYRestaurante(estado, idRestaurante, page, size, idUsuario);
    }

    @Override
    public void asignarPedido(Long idPedido, String estado, Long idUsuario) {
        PedidoModel pedidoModel = buscarPorIdPlato(idPedido);
        if (!EstadoPedido.PENDIENTE.name().equals(pedidoModel.getEstado()))
            throw new PedidoInvalidoException( NO_EXISTE_ESTADOS + EstadoPedido.PENDIENTE);



        if (pedidoModel.getIdChef() != null && !pedidoModel.getIdChef().equals(idUsuario))
            throw new PedidoInvalidoException( NO_EXISTE_EMPLEADO);

        persistencePort.asignarPedido(idPedido, idUsuario, estado);
    }

    @Override
    public PedidoModel buscarPorIdPlato(Long idPedido) {
        PedidoModel pedidoModel = persistencePort.buscarPedidoPorId(idPedido);
        if (pedidoModel == null) {
            throw new PedidoInvalidoException(NO_EXISTE_PLATOS + idPedido);
        }
        return pedidoModel;
    }
}
