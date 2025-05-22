package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.pedido.IPedidoServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.pedido.ITrazabilidadServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.restaurante.UsuarioServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.pedido.PedidoInvalidoException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.*;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.pedido.INotificacionServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedido.IPedidoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedidoplatos.IPedidoPlatoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.platos.PlatoPersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.MensajeError.EMPLEADO_NO_ASOCIADO;
import static com.avargas.devops.pruebas.app.microservicioplazoleta.domain.util.PedidoMensajeError.*;
import static com.avargas.devops.pruebas.app.microservicioplazoleta.domain.util.constants.EstadoPedido.CANCELADO;

@RequiredArgsConstructor
public class PedidoUseCase implements IPedidoServicePort {

    private final IPedidoPersistencePort persistencePort;
    private final PlatoPersistencePort platoPersistencePort;
    private final IPedidoPlatoPersistencePort pedidoPlatoPersistencePort;
    private final INotificacionServicePort notificacionServicePort;
    private final ITrazabilidadServicePort trazabilidadServicePort;
    private final UsuarioServicePort usuarioServicePort;

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
        listarPedidoPorIdRestaurante(idRestaurante,estado, idUsuario);

        return persistencePort.obtenerPedidosPorEstadoYRestaurante(estado, idRestaurante, page, size, idUsuario);
    }

    @Override
    public void asignarPedido(String token, Long idPedido, String estado, Long idUsuario, String pinIngresado,String correo) {
        PedidoModel pedidoModel = buscarPorIdPlato(idPedido);
        String correoCliente = usuarioServicePort.obtenerCorreo(pedidoModel.getIdCliente(), token);

        if (pedidoModel.getIdChef() != null && !pedidoModel.getIdChef().equals(idUsuario)) {
            throw new PedidoInvalidoException(NO_EXISTE_EMPLEADO);
        }

        if (EstadoPedido.LISTO.name().equals(estado) && token!=null) {
            String pinSeguridad = crearPinSeguridad();
            Boolean notificado = notificacionServicePort.notificarUsuario(token, pedidoModel.getIdCliente(), pinSeguridad);

            persistencePort.asignarPinSeguridad(idPedido, estado, pinSeguridad);
            trazabilidadServicePort.crearTraza(token, pedidoModel,  estado,  pedidoModel.getIdChef(), correo, correoCliente);
            return;
        }

        if (EstadoPedido.ENTREGADO.name().equals(estado)) {
            if (!EstadoPedido.LISTO.name().equals(pedidoModel.getEstado())) {
                throw new PedidoInvalidoException(PEDIDO_DISTINTO_LISTO);
            }
            if (pedidoModel.getPinSeguridad() == null || !pedidoModel.getPinSeguridad().equals(pinIngresado)) {
                throw new PedidoInvalidoException(PIN_INCORRECTO);
            }
            persistencePort.asignarPedido(idPedido, idUsuario, estado);
            return;
        }

        if (EstadoPedido.ENTREGADO.name().equals(pedidoModel.getEstado())) {
            throw new PedidoInvalidoException(PEDIDO_ENTREGADO);
        }

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

    @Override
    public void cancelarPedido(Long idPedido, Long idCliente, String correoCliente, String token) {
        PedidoModel pedido = buscarPorIdPlato(idPedido);

        if (!pedido.getIdCliente().equals(idCliente)) {
            throw new PedidoInvalidoException(PEDIDO_DIFERENTE);
        }

        if (!EstadoPedido.PENDIENTE.name().equals(pedido.getEstado())) {
            throw new PedidoInvalidoException(PEDIDO_PREPARACION);
        }
        String correoEmpleado = usuarioServicePort.obtenerCorreo(pedido.getIdCliente(), token);
        persistencePort.asignarPedido(idPedido, null, CANCELADO.name());
        trazabilidadServicePort.crearTraza(token, pedido,  CANCELADO.name(),  pedido.getIdChef(), correoEmpleado, correoCliente);
    }

    @Override
    public List<PedidoModel> filtrarPedidosPorRestaurante(Long idRestaurant) {
        return persistencePort.buscarPedidosPorIdRestaurante(idRestaurant);
    }

    private List<PedidoModel> listarPedidoPorIdRestaurante(Long idRestaurante, String estado, Long idUsuario){
        List<PedidoModel> pedidoModels = persistencePort.buscarPedidosPorIdRestaurante(idRestaurante)
                .stream().filter(pedido -> {
                    boolean estadoCoincide = pedido.getEstado().equalsIgnoreCase(estado);

                    if (!estadoCoincide) return false;

                    if ("PENDIENTE".equalsIgnoreCase(estado)) {
                        return pedido.getIdChef() == null;
                    } else {
                        return idUsuario.equals(pedido.getIdChef());
                    }
                }).toList();
        if (pedidoModels.isEmpty())
            throw new PedidoInvalidoException(EMPLEADO_NO_ASOCIADO.getMessage());

        return pedidoModels;
    }

    private String crearPinSeguridad() {
        int pin = (int) (Math.random() * 10_000);
        return String.format("%04d", pin);
    }


}
