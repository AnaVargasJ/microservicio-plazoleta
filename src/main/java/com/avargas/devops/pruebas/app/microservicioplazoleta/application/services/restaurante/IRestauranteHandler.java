package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.restaurante;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.RestauranteDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.RestauranteResumenDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IRestauranteHandler {
  void crearRestaurante(HttpServletRequest request, RestauranteDTO restauranteDTO);

  List<RestauranteResumenDTO> listarRestaurante(int page, int size);
}
