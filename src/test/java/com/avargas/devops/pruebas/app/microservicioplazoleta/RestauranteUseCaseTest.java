package com.avargas.devops.pruebas.app.microservicioplazoleta;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.restaurante.RestauranteDataException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.restaurante.ValidacionNegocioException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.restaurante.RestaurantePersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.restaurante.RestauranteUseCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RestauranteUseCaseTest {

    @Mock
    private RestaurantePersistencePort persistencePort;

    @InjectMocks
    private RestauranteUseCase restauranteUseCase;




    @Test
    @Order(1)
    void crearRestaurante_exito() {
        RestauranteModel model = crearModeloValido();
        assertDoesNotThrow(() -> restauranteUseCase.crearRestaurante(model));
        verify(persistencePort).guardar(model);
    }
    @Test
    @Order(2)
    void crearRestaurante_nombreNulo_lanzaExcepcion() {
        RestauranteModel model = crearModeloValido();
        model.setNombre(null);

        ValidacionNegocioException ex = assertThrows(
                ValidacionNegocioException.class,
                () -> restauranteUseCase.crearRestaurante(model)
        );
        assertEquals("El nombre no puede ser nulo ni contener solo números.", ex.getMessage());
    }

    @Test
    @Order(3)
    void crearRestaurante_nombreSoloNumeros_lanzaExcepcion() {
        RestauranteModel model = crearModeloValido();
        model.setNombre("123456");

        ValidacionNegocioException ex = assertThrows(
                ValidacionNegocioException.class,
                () -> restauranteUseCase.crearRestaurante(model)
        );
        assertEquals("El nombre no puede ser nulo ni contener solo números.", ex.getMessage());
    }

    @Test
    @Order(4)
    void crearRestaurante_nitInvalido_lanzaExcepcion() {
        RestauranteModel model = crearModeloValido();
        model.setNit("ABC123");

        ValidacionNegocioException ex = assertThrows(
                ValidacionNegocioException.class,
                () -> restauranteUseCase.crearRestaurante(model)
        );
        assertEquals("El NIT debe contener solo números.", ex.getMessage());
    }

    @Test
    @Order(5)
    void crearRestaurante_telefonoInvalido_lanzaExcepcion() {
        RestauranteModel model = crearModeloValido();
        model.setTelefono("tel123");

        ValidacionNegocioException ex = assertThrows(
                ValidacionNegocioException.class,
                () -> restauranteUseCase.crearRestaurante(model)
        );
        assertEquals("Teléfono inválido. Puede comenzar con '+' y tener hasta 13 caracteres numéricos.", ex.getMessage());
    }

    @Test
    @Order(6)
    void getRestauranteModelById_exito() {
        RestauranteModel esperado = crearModeloValido();
        when(persistencePort.getRestauranteModelById(1L)).thenReturn(esperado);

        RestauranteModel resultado = restauranteUseCase.getRestauranteModelById(1L);

        assertEquals(esperado, resultado);
        verify(persistencePort).getRestauranteModelById(1L);
    }

    @Test
    @Order(7)
    void getRestauranteModelById_noExiste_lanzaExcepcion() {
        when(persistencePort.getRestauranteModelById(99L)).thenReturn(null);

        RestauranteDataException ex = assertThrows(
                RestauranteDataException.class,
                () -> restauranteUseCase.getRestauranteModelById(99L)
        );
        assertEquals("No existe restaurante con el 99", ex.getMessage());
    }

    private RestauranteModel crearModeloValido() {
        return RestauranteModel.builder()
                .id(1L)
                .nombre("Juan 123")
                .direccion("Calle 123 #45-67")
                .idPropietario(2L)
                .nit("9012345678")
                .telefono("+573005698325")
                .urlLogo("https://miapp.com/logo.png")
                .build();
    }

}
