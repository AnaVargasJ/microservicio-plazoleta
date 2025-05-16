package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.restaurante;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.restaurante.RestauranteDataException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.restaurante.RestaurantePersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.restaurante.ValidacionNegocioException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.restaurante.RestauranteServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

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

    @Override
    public Page<RestauranteModel> listarRestaurantesPaginados(int page, int size) {
        return persistencePort.listarRestaurantesPaginados(page,size);
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
