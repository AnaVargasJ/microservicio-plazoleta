package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.adapter.restaurante;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PageModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.restaurante.RestaurantePersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.RestauranteEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.restaurantes.IRestauranteEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.restaurantes.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
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

    @Override
    public PageModel<RestauranteModel> listarRestaurantesPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by( "nombre"));
        Page<RestauranteEntity> entidadPage = restauranteRepository.findAllByOrderByNombre(pageable);
        List<RestauranteModel> modelos = entidadPage.getContent().stream()
                .map(entity -> RestauranteModel.builder()
                        .id(entity.getId())
                        .nombre(entity.getNombre())
                        .direccion(entity.getDireccion())
                        .telefono(entity.getTelefono())
                        .urlLogo(entity.getUrlLogo())
                        .idPropietario(entity.getIdPropietario())
                        .nit(entity.getNit())
                        .build())
                .toList();

        return PageModel.<RestauranteModel>builder()
                .content(modelos)
                .currentPage(entidadPage.getNumber())
                .pageSize(entidadPage.getSize())
                .totalElements(entidadPage.getTotalElements())
                .totalPages(entidadPage.getTotalPages())
                .hasNext(entidadPage.hasNext())
                .hasPrevious(entidadPage.hasPrevious())
                .build();
    }
}
