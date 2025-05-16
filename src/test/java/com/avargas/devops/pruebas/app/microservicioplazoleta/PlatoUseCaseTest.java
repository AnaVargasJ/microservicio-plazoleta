package com.avargas.devops.pruebas.app.microservicioplazoleta;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.platos.PlatoDataException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.platos.PlatoInvalidoException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.CategoriaModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.platos.PlatoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.platos.PlatoUseCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlatoUseCaseTest {
    @Mock
    private PlatoPersistencePort platoPersistencePort;
    @InjectMocks
    private PlatoUseCase platoUseCase;

    @Test
    @Order(1)
    void crearPlato_valido_guardaPlato() {
        PlatoModel plato = PlatoModel.builder()
                .nombre("Plato 123")
                .precio(new BigDecimal("25000"))
                .descripcion("Delicioso arroz con pollo")
                .urlImagen("https://miapp.com/img/plato.png")
                .categoriaModel(CategoriaModel.builder()
                        .id(1L)
                        .build())
                .restauranteModel(RestauranteModel.builder()
                        .id(5L)
                        .build())
                .activo(true)
                .build();
        platoUseCase.crearPlato(plato);
        verify(platoPersistencePort).guardar(plato);
    }

    @Test
    @Order(2)
    void crearPlato_null_lanzaExcepcion() {
        PlatoInvalidoException ex = assertThrows(PlatoInvalidoException.class, () -> platoUseCase.crearPlato(null));
        assertEquals("El plato no puede ser nulo.", ex.getMessage());
    }

    @Test
    @Order(3)
    void crearPlato_nombreNull_lanzaExcepcion() {
        PlatoModel plato = PlatoModel.builder()
                .precio(new BigDecimal("25000"))
                .descripcion("Delicioso arroz con pollo")
                .urlImagen("https://miapp.com/img/plato.png")
                .categoriaModel(CategoriaModel.builder()
                        .id(1L)
                        .build())
                .restauranteModel(RestauranteModel.builder()
                        .id(5L)
                        .build())
                .activo(true)
                .build();
        PlatoInvalidoException ex = assertThrows(PlatoInvalidoException.class, () -> platoUseCase.crearPlato(plato));
        assertEquals("El nombre del plato es obligatorio.", ex.getMessage());
    }

    @Test
    @Order(4)
    void crearPlato_nombreBlanco_lanzaExcepcion() {
        PlatoModel plato = PlatoModel.builder()
                .nombre("")
                .precio(new BigDecimal("25000"))
                .descripcion("Delicioso arroz con pollo")
                .urlImagen("https://miapp.com/img/plato.png")
                .categoriaModel(CategoriaModel.builder()
                        .id(1L)
                        .build())
                .restauranteModel(RestauranteModel.builder()
                        .id(5L)
                        .build())
                .activo(true)
                .build();
        PlatoInvalidoException ex = assertThrows(PlatoInvalidoException.class, () -> platoUseCase.crearPlato(plato));
        assertEquals("El nombre del plato es obligatorio.", ex.getMessage());
    }

    @Test
    @Order(5)
    void crearPlato_descripcionNull_lanzaExcepcion() {
        PlatoModel plato = PlatoModel.builder()
                .nombre("Plato 123")
                .precio(new BigDecimal("25000"))
                .urlImagen("https://miapp.com/img/plato.png")
                .categoriaModel(CategoriaModel.builder()
                        .id(1L)
                        .build())
                .restauranteModel(RestauranteModel.builder()
                        .id(5L)
                        .build())
                .activo(true)
                .build();

        PlatoInvalidoException ex = assertThrows(PlatoInvalidoException.class, () -> platoUseCase.crearPlato(plato));
        assertEquals("La descripción del plato es obligatoria.", ex.getMessage());
    }

    @Test
    @Order(6)
    void crearPlato_descripcionBlanco_lanzaExcepcion() {
        PlatoModel plato = PlatoModel.builder()
                .nombre("Plato 123")
                .precio(new BigDecimal("25000"))
                .descripcion("")
                .urlImagen("https://miapp.com/img/plato.png")
                .categoriaModel(CategoriaModel.builder()
                        .id(1L)
                        .build())
                .restauranteModel(RestauranteModel.builder()
                        .id(5L)
                        .build())
                .activo(true)
                .build();

        PlatoInvalidoException ex = assertThrows(PlatoInvalidoException.class, () -> platoUseCase.crearPlato(plato));
        assertEquals("La descripción del plato es obligatoria.", ex.getMessage());
    }

    @Test
    @Order(7)
    void crearPlato_precioNull_lanzaExcepcion() {
        PlatoModel plato = PlatoModel.builder()
                .nombre("Plato 123")
                .descripcion("Delicioso arroz con pollo")
                .urlImagen("https://miapp.com/img/plato.png")
                .categoriaModel(CategoriaModel.builder()
                        .id(1L)
                        .build())
                .restauranteModel(RestauranteModel.builder()
                        .id(5L)
                        .build())
                .activo(true)
                .build();

        PlatoInvalidoException ex = assertThrows(PlatoInvalidoException.class, () -> platoUseCase.crearPlato(plato));
        assertEquals("El precio debe ser mayor que cero.", ex.getMessage());

    }

    @Test
    @Order(8)
    void getPlatoModelById_existente_retornaPlato() {
        PlatoModel plato = PlatoModel.builder()
                .id(1L)
                .nombre("Plato 123")
                .descripcion("Delicioso arroz con pollo")
                .urlImagen("https://miapp.com/img/plato.png")
                .categoriaModel(CategoriaModel.builder()
                        .id(1L)
                        .build())
                .restauranteModel(RestauranteModel.builder()
                        .id(5L)
                        .build())
                .activo(true)
                .build();
        when(platoPersistencePort.getPlatoModelById(1L)).thenReturn(plato);

        PlatoModel resultado = platoUseCase.getPlatoModelById(1L);

        assertEquals(plato, resultado);
    }

    @Test
    void getPlatoModelById_noExistente_lanzaExcepcion() {
        when(platoPersistencePort.getPlatoModelById(99L)).thenReturn(null);

        PlatoDataException ex = assertThrows(PlatoDataException.class, () -> platoUseCase.getPlatoModelById(99L));
        assertEquals("No existe  plato con el 99", ex.getMessage());
    }

    @Test
    @Order(9)
    void modificarPlato_actualizaDescripcionYPrecio() {
        Long id = 1L;
        PlatoModel existente = PlatoModel.builder()
                .id(id)
                .nombre("Plato")
                .descripcion("Antigua")
                .precio(BigDecimal.TEN)
                .build();

        PlatoModel actualizado = PlatoModel.builder()
                .id(id)
                .nombre("Plato")
                .descripcion("Nueva")
                .precio(BigDecimal.valueOf(20))
                .build();

        when(platoPersistencePort.getPlatoModelById(id)).thenReturn(existente);

        platoUseCase.modificarPlato(id, actualizado);

        assertEquals("Nueva", existente.getDescripcion());
        assertEquals(BigDecimal.valueOf(20), existente.getPrecio());
        verify(platoPersistencePort).updatePlatoDescripcionPrecio(id, "Nueva", BigDecimal.valueOf(20));
    }

    @Test
    void modificarPlato_descripcionNull_precioNoNull() {
        Long id = 1L;
        PlatoModel existente = PlatoModel.builder()
                .id(id)
                .nombre("Plato")
                .descripcion("Antigua")
                .precio(BigDecimal.TEN)
                .build();

        PlatoModel actualizado = PlatoModel.builder()
                .id(id)
                .nombre("Plato")
                .descripcion("Nueva")
                .precio(null)
                .build();

        when(platoPersistencePort.getPlatoModelById(id)).thenReturn(existente);

        platoUseCase.modificarPlato(id, actualizado);

        assertEquals("Nueva", existente.getDescripcion());
        assertEquals(BigDecimal.valueOf(10), existente.getPrecio());
        verify(platoPersistencePort).updatePlatoDescripcionPrecio(id, "Nueva", null);
    }

    @Test
    @Order(10)
    void activarDesactivarPlato_debeActualizarEstadoCuandoElPropietarioEsValido() {

        Long idPlato = 1L;
        Long idPropietario = 10L;
        boolean nuevoEstado = false;

        PlatoModel existente = PlatoModel.builder()
                .id(idPlato)
                .nombre("Plato")
                .activo(true)
                .build();

        when(platoPersistencePort.getPlatoModelById(idPlato)).thenReturn(existente);
        when(platoPersistencePort.validarPropietarioDelPlato(idPlato, idPropietario)).thenReturn(true);


        platoUseCase.activarDesactivarPlato(idPlato, nuevoEstado, idPropietario);


        verify(platoPersistencePort).activarDesactivarPlato(idPlato, nuevoEstado);
    }

    @Test
    @Order(11)
    void activarDesactivarPlato_debeLanzarExcepcionCuandoElPropietarioNoEsValido() {

        Long idPlato = 1L;
        Long idPropietario = 999L;

        PlatoModel existente = PlatoModel.builder()
                .id(idPlato)
                .nombre("Plato")
                .activo(true)
                .build();

        when(platoPersistencePort.getPlatoModelById(idPlato)).thenReturn(existente);
        when(platoPersistencePort.validarPropietarioDelPlato(idPlato, idPropietario)).thenReturn(false);


        assertThrows(PlatoInvalidoException.class, () ->
                platoUseCase.activarDesactivarPlato(idPlato, false, idPropietario)
        );

        verify(platoPersistencePort, never()).activarDesactivarPlato(anyLong(), anyBoolean());
    }


}
