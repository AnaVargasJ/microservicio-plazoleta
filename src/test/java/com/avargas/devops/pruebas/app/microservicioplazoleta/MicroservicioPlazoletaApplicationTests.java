package com.avargas.devops.pruebas.app.microservicioplazoleta;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.RestauranteDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.restaurante.impl.RestauranteHandler;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.restaurantes.RestauranteRepository;
import com.github.tomakehurst.wiremock.http.Fault;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MicroservicioPlazoletaApplicationTests {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteHandler restauranteService;

    @Value("${microserviciopropietarios}")
    private String urlPropietarios;

    static WireMockServer wireMockServer;

    @BeforeAll
    static void startWireMock() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8089));
        wireMockServer.start();
        configureFor("localhost", 8089);
    }

    @AfterAll
    static void stopWireMock() {
        wireMockServer.stop();
    }

    @BeforeEach
    void setupStub() {
        stubFor(get(urlPathEqualTo("/api/v1/usuarios/buscarPorCorreo/elcorreo12o@correo.com"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("""
                            {
                              "idUsuario": 41,
                              "nombre": "Juan",
                              "apellido": "Pérez",
                              "numeroDocumento": "03665652222",
                              "celular": "+573121234566",
                              "fechaNacimiento": "01/01/2001",
                              "correo": "elcorreo12o@correo.com",
                              "rol": {
                                "idRol": 2,
                                "nombre": "PROP",
                                "descripcion": "Propietario"
                              }
                            }
                        """)));
    }

    @Test
    void crearRestaurante_CuandoRolEsPropietario_DeberiaCrearYRetornar201(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "test-token");

        RestauranteDTO dto = RestauranteDTO.builder()
                .nombre("La taquiza")
                .direccion("Calle 123 #45-67")
                .correo("elcorreo12o@correo.com")  
                .nit("9012345678")
                .telefono("+573005698325")
                .urlLogo("https://miapp.com/logo.png")
                .build();

        ResponseEntity<?> response = restauranteService.crearRestaurante(request, dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(restauranteRepository.findAll().stream()
                .anyMatch(r -> r.getNombre().equals("La taquiza")));
    }

    @Test
    void crearRestaurante_CuandoFallaConsultaCorreo_DeberiaRetornar404() {
        // Simular respuesta 404 desde el microservicio de propietarios
        stubFor(get(urlPathEqualTo("/api/v1/usuarios/buscarPorCorreo/elcorreo12o@correo.com"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                        {
                          "codigo": 404,
                          "mensaje": "No se encontró el usuario",
                          "error": "No se encontró el usuario"
                        }
                    """)));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "test-token");

        RestauranteDTO dto = RestauranteDTO.builder()
                .nombre("Restaurante Falla")
                .direccion("Calle Falsa 123")
                .correo("elcorreo12o@correo.com") // debe coincidir con stub
                .nit("9999999999")
                .telefono("+573001112233")
                .urlLogo("https://miapp.com/logo-falla.png")
                .build();

        ResponseEntity<?> response = restauranteService.crearRestaurante(request, dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertEquals("Error al consultar el rol del usuario: ", body.get("error"));
        assertEquals("No se encontró el usuario", body.get("mensaje"));
        assertEquals(404, body.get("codigo"));
    }

    @Test
    void crearRestaurante_CuandoOcurreExcepcion_DeberiaRetornar500() {

        stubFor(get(urlPathEqualTo("/api/v1/usuarios/buscarPorCorreo/elcorreo12o@correo.com"))
                .willReturn(aResponse()
                        .withFault(Fault.MALFORMED_RESPONSE_CHUNK)));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "test-token");

        RestauranteDTO dto = RestauranteDTO.builder()
                .nombre("Restaurante Excepcion")
                .direccion("Calle Error 500")
                .correo("elcorreo12o@correo.com") // debe coincidir con stub
                .nit("1234567890")
                .telefono("+573001112233")
                .urlLogo("https://miapp.com/logo-error.png")
                .build();

        ResponseEntity<?> response = restauranteService.crearRestaurante(request, dto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error al crear el Restairamte", response.getBody());
    }


}
