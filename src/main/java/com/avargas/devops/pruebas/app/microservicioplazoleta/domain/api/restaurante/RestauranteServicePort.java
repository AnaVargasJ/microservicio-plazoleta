package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.restaurante;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;

public interface RestauranteServicePort {

    void crearRestaurante(RestauranteModel restauranteModel);

    RestauranteModel getRestauranteModelById(Long id);


}
