package com.avargas.devops.pruebas.app.microservicioplazoleta;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.pedido.PedidoInvalidoException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoPlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;
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

import static com.avargas.devops.pruebas.app.microservicioplazoleta.domain.util.PedidoMensajeError.PEDIDO_EN_PROCESO;
import static com.avargas.devops.pruebas.app.microservicioplazoleta.domain.util.PedidoMensajeError.PLATOS_DISTINTO_RESTAURANTE;
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
                .id(99L) // Simular que se generó el ID
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

