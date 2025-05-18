package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.restaurante;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PageModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;



public interface RestaurantePersistencePort {

    RestauranteModel guardar(RestauranteModel restauranteModel);
    RestauranteModel getRestauranteModelById(Long id);
    PageModel<RestauranteModel> listarRestaurantesPaginados(int page, int size);

}
