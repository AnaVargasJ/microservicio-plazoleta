package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.restaurante;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.RestauranteDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface IRestauranteHandler {
  void crearRestaurante(HttpServletRequest request, RestauranteDTO restauranteDTO);


}
