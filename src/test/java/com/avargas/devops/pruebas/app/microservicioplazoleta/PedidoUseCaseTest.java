package com.avargas.devops.pruebas.app.microservicioplazoleta;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.pedido.PedidoInvalidoException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.*;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedido.IPedidoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedidoplatos.IPedidoPlatoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.platos.PlatoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.pedido.PedidoUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
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
class PedidoUseCaseTest {

    @Mock
    private IPedidoPersistencePort pedidoPersistencePort;

    @Mock
    private PlatoPersistencePort platoPersistencePort;

    @Mock
    private IPedidoPlatoPersistencePort pedidoPlatoPersistencePort;

    @InjectMocks
    private PedidoUseCase pedidoUseCase;

    @Test
    @Order(1)
    void crearPedido_valido_guardaPedidoYPlatos() {

        PedidoModel pedido = buildPedidoModel(1L);
        PedidoModel pedidoConId = PedidoModel.builder()
                .id(99L)
                .idCliente(pedido.getIdCliente())
                .restauranteModel(pedido.getRestauranteModel())
                .platos(pedido.getPlatos())
                .estado(pedido.getEstado())
                .fecha(pedido.getFecha())
                .build();

        when(pedidoPersistencePort.existePedidoEnProceso(100L)).thenReturn(false);
        when(platoPersistencePort.getPlatoModelById(10L)).thenReturn(pedido.getPlatos().get(0).getPlatoModel());
        when(pedidoPersistencePort.guardarPedido(any(PedidoModel.class))).thenReturn(pedidoConId);

        pedidoUseCase.crearPedido(pedido);


        verify(pedidoPersistencePort).guardarPedido(any(PedidoModel.class));
        verify(pedidoPlatoPersistencePort).guardarPlatosDePedido(anyList(), eq(99L));
    }

    @Test
    @Order(2)
    void crearPedido_yaExistePedidoEnProceso_lanzaExcepcion() {
        PedidoModel pedido = buildPedidoModel(1L);

        when(pedidoPersistencePort.existePedidoEnProceso(100L)).thenReturn(true);

        PedidoInvalidoException ex = assertThrows(PedidoInvalidoException.class, () ->
                pedidoUseCase.crearPedido(pedido)
        );
        assertEquals(PEDIDO_EN_PROCESO, ex.getMessage());
        verify(pedidoPersistencePort, never()).guardarPedido(any());
        verify(pedidoPlatoPersistencePort, never()).guardarPlatosDePedido(any(), any());
    }

    @Test
    @Order(3)
    void crearPedido_platoDeOtroRestaurante_lanzaExcepcion() {
        PedidoModel pedido = buildPedidoModel(1L);
        RestauranteModel otroRestaurante = RestauranteModel.builder().id(99L).build();
        PlatoModel platoErroneo = PlatoModel.builder()
                .id(10L)
                .restauranteModel(otroRestaurante)
                .build();

        when(pedidoPersistencePort.existePedidoEnProceso(100L)).thenReturn(false);
        when(platoPersistencePort.getPlatoModelById(10L)).thenReturn(platoErroneo);

        PedidoInvalidoException ex = assertThrows(PedidoInvalidoException.class, () ->
                pedidoUseCase.crearPedido(pedido)
        );
        assertEquals(PLATOS_DISTINTO_RESTAURANTE, ex.getMessage());
        verify(pedidoPersistencePort, never()).guardarPedido(any());
        verify(pedidoPlatoPersistencePort, never()).guardarPlatosDePedido(any(), any());
    }

    @Test
    @Order(4)
    void obtenerPedidosPorEstadoYRestaurante_valido_retornaPedidos() {
        String estado = "PENDIENTE";
        Long idRestaurante = 1L;
        Long idUsuario = 10L;
        int page = 0;
        int size = 10;

        PedidoModel pedido = PedidoModel.builder()
                .id(1L)
                .estado(estado)
                .idChef(null) // obligatorio para estado PENDIENTE
                .restauranteModel(RestauranteModel.builder().id(idRestaurante).build())
                .build();

        when(pedidoPersistencePort.buscarPedidosPorIdRestaurante(idRestaurante))
                .thenReturn(List.of(pedido));

        PageModel<PedidoModel> pageModelMock = PageModel.<PedidoModel>builder()
                .content(List.of(pedido))
                .currentPage(page)
                .pageSize(size)
                .totalElements(1L)
                .totalPages(1)
                .hasNext(false)
                .hasPrevious(false)
                .build();

        when(pedidoPersistencePort.obtenerPedidosPorEstadoYRestaurante(estado, idRestaurante, page, size, idUsuario))
                .thenReturn(pageModelMock);

        PageModel<PedidoModel> resultado = pedidoUseCase.obtenerPedidosPorEstadoYRestaurante(
                estado, idRestaurante, page, size, idUsuario);

        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(pedidoPersistencePort).buscarPedidosPorIdRestaurante(idRestaurante);
        verify(pedidoPersistencePort).obtenerPedidosPorEstadoYRestaurante(estado, idRestaurante, page, size, idUsuario);
    }


    @Test
    @Order(5)
    void asignarPedido_valido_ejecutaAsignacion() {
        PedidoModel pedido = PedidoModel.builder()
                .id(1L)
                .estado(EstadoPedido.PENDIENTE.name())
                .idChef(null)
                .build();

        when(pedidoPersistencePort.buscarPedidoPorId(1L)).thenReturn(pedido);

        assertDoesNotThrow(() -> pedidoUseCase.asignarPedido(1L, EstadoPedido.EN_PREPARACION.name(), 100L));

        verify(pedidoPersistencePort).asignarPedido(1L, 100L, EstadoPedido.EN_PREPARACION.name());
    }



    @Test
    @Order(6)
    void asignarPedido_yaAsignadoAOtroEmpleado_lanzaExcepcion() {
        PedidoModel pedido = PedidoModel.builder()
                .id(1L)
                .estado(EstadoPedido.PENDIENTE.name())
                .idChef(200L)
                .build();

        when(pedidoPersistencePort.buscarPedidoPorId(1L)).thenReturn(pedido);

        PedidoInvalidoException ex = assertThrows(
                PedidoInvalidoException.class,
                () -> pedidoUseCase.asignarPedido(1L, EstadoPedido.EN_PREPARACION.name(), 100L)
        );


        assertTrue(ex.getMessage().contains( NO_EXISTE_EMPLEADO));
        verify(pedidoPersistencePort, never()).asignarPedido(any(), any(), any());
    }


    @Test
    @Order(7)
    void obtenerPedidosPorEstadoYRestaurante_sinPedidosFiltrados_lanzaExcepcion() {
        String estado = "PENDIENTE";
        Long idRestaurante = 1L;
        Long idUsuario = 10L;
        int page = 0;
        int size = 10;

        // Pedido con estado que no coincide (para que el filtro lo descarte)
        PedidoModel pedido = PedidoModel.builder()
                .id(1L)
                .estado("ENTREGADO") // No coincide con "PENDIENTE"
                .idChef(99L) // También se descarta por idChef != null
                .restauranteModel(RestauranteModel.builder().id(idRestaurante).build())
                .build();

        // Mock correcto: el pedido sí pertenece al restaurante con ID 1
        when(pedidoPersistencePort.buscarPedidosPorIdRestaurante(eq(idRestaurante)))
                .thenReturn(List.of(pedido));

        PedidoInvalidoException ex = assertThrows(
                PedidoInvalidoException.class,
                () -> pedidoUseCase.obtenerPedidosPorEstadoYRestaurante(
                        estado, idRestaurante, page, size, idUsuario)
        );

        assertEquals(EMPLEADO_NO_ASOCIADO.getMessage(), ex.getMessage());

        verify(pedidoPersistencePort).buscarPedidosPorIdRestaurante(idRestaurante);
        verify(pedidoPersistencePort, never()).obtenerPedidosPorEstadoYRestaurante(any(), any(), anyInt(), anyInt(), any());
    }






    private PedidoModel buildPedidoModel(Long idRestaurante) {
        RestauranteModel restaurante = RestauranteModel.builder()
                .id(idRestaurante)
                .build();

        PlatoModel plato = PlatoModel.builder()
                .id(10L)
                .restauranteModel(restaurante)
                .build();

        PedidoPlatoModel pedidoPlato = PedidoPlatoModel.builder()
                .platoModel(plato)
                .cantidad(2)
                .build();

        return PedidoModel.builder()
                .idCliente(100L)
                .restauranteModel(restaurante)
                .platos(List.of(pedidoPlato))
                .build();
    }



}

