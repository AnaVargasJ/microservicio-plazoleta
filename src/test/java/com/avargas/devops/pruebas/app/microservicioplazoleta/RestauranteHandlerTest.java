package com.avargas.devops.pruebas.app.microservicioplazoleta;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.RestauranteDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.restaurante.IRestauranteRequestMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.restaurante.impl.RestauranteHandler;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.restaurante.RestauranteServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.restaurante.UsuarioServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteHandlerTest {

    @Mock
    private RestauranteServicePort restauranteServicePort;

    @Mock
    private UsuarioServicePort usuarioServicePort;

    @Mock
    private IRestauranteRequestMapper restauranteRequestMapper;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private RestauranteHandler restauranteHandler;

    @Test
    void crearRestaurante_deberiaCrearRestauranteConDatosCorrectos(){

        RestauranteDTO dto = RestauranteDTO.builder()
                .nombre("Juan 123")
                .direccion("Calle 123 #45-67")
                .correo("user1@example.com")
                .nit("9012345678")
                .telefono("+573005698325")
                .urlLogo("https://miapp.com/logo.png")
                .build();

        RestauranteModel modelMock = new RestauranteModel();

        when(restauranteRequestMapper.toModel(any(RestauranteDTO.class))).thenReturn(modelMock);
        when(usuarioServicePort.usuarioEsPropietario(eq(dto.getCorreo()), any())).thenReturn(1L);

        restauranteHandler.crearRestaurante(httpServletRequest, dto);
        assertEquals(1L, modelMock.getIdPropietario());
        verify(restauranteServicePort).crearRestaurante(modelMock);
    }
}
