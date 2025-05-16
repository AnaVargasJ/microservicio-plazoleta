package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.restaurante;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.RestauranteDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface IRestauranteController {

    ResponseEntity<?> crearRestaurante(HttpServletRequest request, RestauranteDTO restauranteDTO);

    ResponseEntity<?> listarRestaurantes(HttpServletRequest request,int page, int size);

}
