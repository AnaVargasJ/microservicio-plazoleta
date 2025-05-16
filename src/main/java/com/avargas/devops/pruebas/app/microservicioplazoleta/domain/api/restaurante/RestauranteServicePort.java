package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.restaurante;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;
import org.springframework.data.domain.Page;

public interface RestauranteServicePort {

    void crearRestaurante(RestauranteModel restauranteModel);

    RestauranteModel getRestauranteModelById(Long id);

    Page<RestauranteModel> listarRestaurantesPaginados(int page, int size);

}
