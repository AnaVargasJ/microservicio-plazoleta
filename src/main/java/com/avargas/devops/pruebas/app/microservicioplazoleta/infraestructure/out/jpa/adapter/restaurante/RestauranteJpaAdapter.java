package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.adapter.restaurante;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.restaurante.RestaurantePersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.RestauranteEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.restaurantes.IRestauranteEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.restaurantes.RestauranteRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RestauranteJpaAdapter implements RestaurantePersistencePort {

    private final RestauranteRepository restauranteRepository;
    private final IRestauranteEntityMapper entityMapper;

    @Override
    public RestauranteModel guardar(RestauranteModel restauranteModel) {
        RestauranteEntity restauranteEntity = restauranteRepository.save(entityMapper.toRestauranteEntity(restauranteModel));
        return entityMapper.toRestauranteModel(restauranteEntity);
    }

    @Override
    public RestauranteModel getRestauranteModelById(Long id) {
        Optional<RestauranteEntity> filtrarPorId = restauranteRepository.findById(id);
        return filtrarPorId.map(entityMapper::toRestauranteModel).orElseGet(null);
    }
}
