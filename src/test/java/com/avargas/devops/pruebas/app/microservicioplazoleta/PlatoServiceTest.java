package com.avargas.devops.pruebas.app.microservicioplazoleta;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.platos.impl.PlatoService;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.validation.ValidationService;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.converter.GenericConverter;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.Categoria;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.Restaurante;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.categorias.CategoriaRepository;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.platos.PlatoRepository;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.restaurantes.RestauranteRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlatoServiceTest {

    @Autowired
    private  PlatoService platoService;

    @Autowired
    private  PlatoRepository platoRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private  RestauranteRepository restauranteRepository;

    @Autowired
    private  CategoriaRepository categoriaRepository;


    @Test
    @Order(1)
    void crearPlatoExitoso201() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Restaurante restaurante = Restaurante.builder()
                .nombre("La Taquiza")
                .direccion("Calle 123 #45-67")
                .telefono("+573005698325")
                .urlLogo("https://miapp.com/logo.png")
                .idPropietario(41L)
                .build();
      restaurante =  restauranteRepository.save(restaurante);

        Categoria categoria =categoria = Categoria.builder()
                .nombre("Platos fuertes")
                .description("Comidas principales con proteína y guarnición")
                .build();
       categoria =  categoriaRepository.save(categoria);

        PlatoDTO platoDTO = PlatoDTO.builder()
                .nombre("Plato 123456")
                .precio(BigDecimal.valueOf(25000))
                .descripcion("Delicioso arroz con pollo")
                .urlImagen("https://miapp.com/img/plato.png")
                .idCategoria(categoria.getId())
                .idRestaurante(restaurante.getId())
                .build();

        ResponseEntity<?> response = platoService.crearPlato(request, platoDTO);
        assertEquals("Plato creado correctamente", response.getBody());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(platoRepository.findAll().stream().anyMatch(r -> r.getNombre().equals("Plato 123456")));
    }

    @Test
    @Order(2)
    void NombrePlatoObligatorio400() {

        MockHttpServletRequest request = new MockHttpServletRequest();

        PlatoDTO platoDTO = PlatoDTO.builder()
                .precio(BigDecimal.valueOf(25000))
                .descripcion("Delicioso arroz con pollo")
                .urlImagen("https://miapp.com/img/plato.png")
                .build();

        BindingResult bindingResult = new BeanPropertyBindingResult(platoDTO, "platoDTO");
        ResponseEntity<?> response =  validationService.validate(bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    @Order(3)
    void crearPlatoException500() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        PlatoDTO platoDTO = new PlatoDTO();

        ResponseEntity<?> response = platoService.crearPlato(request, platoDTO);
        assertEquals("Error al crear el plato", response.getBody());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }



}
