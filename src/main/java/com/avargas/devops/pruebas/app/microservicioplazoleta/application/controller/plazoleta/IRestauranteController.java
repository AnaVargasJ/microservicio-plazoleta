package com.avargas.devops.pruebas.app.microservicioplazoleta.application.controller.plazoleta;

import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.commons.domains.generic.RestauranteDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface IRestauranteController {

    ResponseEntity<?> crearRestaurante(HttpServletRequest request, RestauranteDTO restauranteDTO);
}
