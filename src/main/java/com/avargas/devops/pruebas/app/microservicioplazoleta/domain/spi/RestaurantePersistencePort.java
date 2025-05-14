package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;

public interface RestaurantePersistencePort {

    RestauranteModel guardar(RestauranteModel restauranteModel);
    RestauranteModel getRestauranteModelById(Long id);


}
