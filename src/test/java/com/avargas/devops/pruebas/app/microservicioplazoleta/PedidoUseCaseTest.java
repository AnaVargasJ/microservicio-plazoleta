package com.avargas.devops.pruebas.app.microservicioplazoleta;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.pedido.INotificacionServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.pedido.PedidoInvalidoException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.*;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedido.IPedidoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedidoplatos.IPedidoPlatoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.platos.PlatoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.pedido.PedidoUseCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.MensajeError.EMPLEADO_NO_ASOCIADO;
import static com.avargas.devops.pruebas.app.microservicioplazoleta.domain.util.PedidoMensajeError.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PedidoUseCaseTest {

    @Mock
    private IPedidoPersistencePort pedidoPersistencePort;

    @Mock
    private PlatoPersistencePort platoPersistencePort;

    @Mock
    private IPedidoPlatoPersistencePort pedidoPlatoPersistencePort;

    @Mock
    private INotificacionServicePort notificacionServicePort;

    @InjectMocks
    private PedidoUseCase pedidoUseCase;

    private PedidoModel buildPedidoBase() {
        RestauranteModel restaurante = RestauranteModel.builder().id(1L).build();
        PlatoModel plato = PlatoModel.builder().id(10L).restauranteModel(restaurante).build();
        PedidoPlatoModel pedidoPlato = PedidoPlatoModel.builder().platoModel(plato).build();
        return PedidoModel.builder()
                .id(1L)
                .estado("PENDIENTE")
                .idCliente(99L)
                .restauranteModel(restaurante)
                .platos(List.of(pedidoPlato))
                .build();
    }

    @Test
    @Order(1)
    void crearPedido_exitoso() {
        PedidoModel pedido = buildPedidoBase();
        when(pedidoPersistencePort.existePedidoEnProceso(99L)).thenReturn(false);
        when(platoPersistencePort.getPlatoModelById(10L)).thenReturn(pedido.getPlatos().get(0).getPlatoModel());
        when(pedidoPersistencePort.guardarPedido(any())).thenReturn(pedido);

        assertDoesNotThrow(() -> pedidoUseCase.crearPedido(pedido));
        verify(pedidoPersistencePort).guardarPedido(any());
        verify(pedidoPlatoPersistencePort).guardarPlatosDePedido(any(), eq(pedido.getId()));
    }

    @Test
    @Order(2)
    void crearPedido_conPedidoEnProceso_lanzaExcepcion() {
        PedidoModel pedido = buildPedidoBase();
        when(pedidoPersistencePort.existePedidoEnProceso(99L)).thenReturn(true);

        assertThrows(PedidoInvalidoException.class, () -> pedidoUseCase.crearPedido(pedido));
    }

    @Test
    @Order(3)
    void crearPedido_conPlatosDeOtroRestaurante_lanzaExcepcion() {
        PedidoModel pedido = buildPedidoBase();
        RestauranteModel otroRestaurante = RestauranteModel.builder().id(2L).build();
        PlatoModel platoExterno = PlatoModel.builder().id(10L).restauranteModel(otroRestaurante).build();
        when(pedidoPersistencePort.existePedidoEnProceso(99L)).thenReturn(false);
        when(platoPersistencePort.getPlatoModelById(10L)).thenReturn(platoExterno);

        assertThrows(PedidoInvalidoException.class, () -> pedidoUseCase.crearPedido(pedido));
    }

    @Test
    @Order(4)
    void obtenerPedidosPorEstadoYRestaurante_exitoso() {
        when(pedidoPersistencePort.buscarPedidosPorIdRestaurante(anyLong()))
                .thenReturn(List.of(buildPedidoBase()));
        when(pedidoPersistencePort.obtenerPedidosPorEstadoYRestaurante(any(), anyLong(), anyInt(), anyInt(), anyLong()))
                .thenReturn(PageModel.<PedidoModel>builder().build());

        assertDoesNotThrow(() ->
                pedidoUseCase.obtenerPedidosPorEstadoYRestaurante("PENDIENTE", 1L, 0, 5, 10L));
    }

    @Test
    @Order(5)
    void listarPedidoPorIdRestaurante_vacio_lanzaExcepcion() {
        when(pedidoPersistencePort.buscarPedidosPorIdRestaurante(1L)).thenReturn(List.of());

        assertThrows(PedidoInvalidoException.class, () ->
                pedidoUseCase.obtenerPedidosPorEstadoYRestaurante("PENDIENTE", 1L, 0, 5, 10L));
    }

    @Test
    @Order(6)
    void asignarPedido_LISTO_enviaNotificacionYGuardaPin() {
        PedidoModel pedido = buildPedidoBase();
        pedido.setEstado("PENDIENTE");
        when(pedidoPersistencePort.buscarPedidoPorId(1L)).thenReturn(pedido);
        when(notificacionServicePort.notificarUsuario(anyString(), anyLong(), anyString())).thenReturn(true);

        pedidoUseCase.asignarPedido("Bearer token", 1L, "LISTO", 5L, null);

        verify(pedidoPersistencePort).asignarPinSeguridad(eq(1L), eq("LISTO"), anyString());
    }

    @Test
    @Order(7)
    void asignarPedido_ENTREGADO_conEstadoNoListo_lanzaExcepcion() {
        PedidoModel pedido = buildPedidoBase();
        pedido.setEstado("PENDIENTE");
        when(pedidoPersistencePort.buscarPedidoPorId(1L)).thenReturn(pedido);

        assertThrows(PedidoInvalidoException.class, () ->
                pedidoUseCase.asignarPedido(null, 1L, "ENTREGADO", 5L, "0001"));
    }

    @Test
    @Order(8)
    void asignarPedido_ENTREGADO_conPinIncorrecto_lanzaExcepcion() {
        PedidoModel pedido = buildPedidoBase();
        pedido.setEstado("LISTO");
        pedido.setPinSeguridad("9999");
        when(pedidoPersistencePort.buscarPedidoPorId(1L)).thenReturn(pedido);

        assertThrows(PedidoInvalidoException.class, () ->
                pedidoUseCase.asignarPedido(null, 1L, "ENTREGADO", 5L, "0000"));
    }

    @Test
    @Order(9)
    void asignarPedido_ENTREGADO_exitoso() {
        PedidoModel pedido = buildPedidoBase();
        pedido.setEstado("LISTO");
        pedido.setPinSeguridad("1234");
        when(pedidoPersistencePort.buscarPedidoPorId(1L)).thenReturn(pedido);

        assertDoesNotThrow(() ->
                pedidoUseCase.asignarPedido(null, 1L, "ENTREGADO", 5L, "1234"));
        verify(pedidoPersistencePort).asignarPedido(1L, 5L, "ENTREGADO");
    }

    @Test
    @Order(10)
    void asignarPedido_estadoActualYaEntregado_lanzaExcepcion() {
        PedidoModel pedido = buildPedidoBase();
        pedido.setEstado("ENTREGADO");
        when(pedidoPersistencePort.buscarPedidoPorId(1L)).thenReturn(pedido);

        assertThrows(PedidoInvalidoException.class, () ->
                pedidoUseCase.asignarPedido(null, 1L, "EN_PREPARACION", 5L, "0000"));
    }

    @Test
    @Order(11)
    void asignarPedido_estadoOtroValido_procesaCorrectamente() {
        PedidoModel pedido = buildPedidoBase();
        pedido.setEstado("EN_PREPARACION");
        when(pedidoPersistencePort.buscarPedidoPorId(1L)).thenReturn(pedido);

        assertDoesNotThrow(() ->
                pedidoUseCase.asignarPedido(null, 1L, "PENDIENTE", 5L, null));
        verify(pedidoPersistencePort).asignarPedido(1L, 5L, "PENDIENTE");
    }

    @Test
    @Order(12)
    void buscarPorIdPlato_noExistePedido_lanzaExcepcion() {
        when(pedidoPersistencePort.buscarPedidoPorId(100L)).thenReturn(null);
        assertThrows(PedidoInvalidoException.class, () -> pedidoUseCase.buscarPorIdPlato(100L));
    }

    @Test
    @Order(13)
    void buscarPorIdPlato_exitoso() {
        PedidoModel pedido = buildPedidoBase();
        when(pedidoPersistencePort.buscarPedidoPorId(1L)).thenReturn(pedido);
        PedidoModel result = pedidoUseCase.buscarPorIdPlato(1L);
        assertEquals(pedido, result);
    }
}

