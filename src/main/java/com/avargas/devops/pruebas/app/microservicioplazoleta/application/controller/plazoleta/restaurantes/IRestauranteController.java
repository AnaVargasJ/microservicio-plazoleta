package com.avargas.devops.pruebas.app.microservicioplazoleta.application.controller.plazoleta.restaurantes;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.RestauranteDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IRestauranteController {

    ResponseEntity<?> crearRestaurante(HttpServletRequest request, RestauranteDTO restauranteDTO, BindingResult result );
}
