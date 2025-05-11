package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.restaurante;

import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.commons.domains.generic.RestauranteDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface IRestauranteService {

    ResponseEntity<?> crearRestaurante(HttpServletRequest request, RestauranteDTO restauranteDTO);


}
