package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.RestauranteDataException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.RestaurantePersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.ValidacionNegocioException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.RestauranteServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestauranteUseCase implements RestauranteServicePort {

    private final RestaurantePersistencePort persistencePort;
    @Override
    public void  crearRestaurante(RestauranteModel restauranteModel) {

        validarCampos(restauranteModel);
        persistencePort.guardar(restauranteModel);
    }

    @Override
    public RestauranteModel getRestauranteModelById(Long id) {
        RestauranteModel restauranteModel = persistencePort.getRestauranteModelById(id);
        if (restauranteModel == null) {
            throw new RestauranteDataException("No existe restaurante con el " + id);
        }
        return restauranteModel;

    }

    private void validarCampos(RestauranteModel restauranteModel) {
        if (restauranteModel.getNombre() == null || restauranteModel.getNombre().matches("^\\d+$")) {
            throw new ValidacionNegocioException("El nombre no puede ser nulo ni contener solo números.");
        }

        if (restauranteModel.getNit() == null || !restauranteModel.getNit().matches("^\\d+$")) {
            throw new ValidacionNegocioException("El NIT debe contener solo números.");
        }

        if (restauranteModel.getTelefono() == null || !restauranteModel.getTelefono().matches("^\\+?\\d{1,13}$")) {
            throw new ValidacionNegocioException("Teléfono inválido. Puede comenzar con '+' y tener hasta 13 caracteres numéricos.");
        }


    }
}
