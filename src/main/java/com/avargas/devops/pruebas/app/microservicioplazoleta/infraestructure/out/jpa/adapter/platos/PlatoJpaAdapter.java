package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.adapter.platos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.platos.PlatoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.CategoriaEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PlatoEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.RestauranteEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.platos.IPlatoEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.platos.PlatoRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
public class PlatoJpaAdapter  implements PlatoPersistencePort {
    private final PlatoRepository platoRepository;
    private final IPlatoEntityMapper entityMapper;

    @Override
    public PlatoModel guardar(PlatoModel platoModel) {
        PlatoEntity platoEntity = entityMapper.toPlatoEntity(platoModel);
        platoEntity.setCategoriaEntity(
                CategoriaEntity.builder()
                        .id(platoModel.getCategoriaModel().getId())
                        .build()
        );

        platoEntity.setRestauranteEntity(
                RestauranteEntity.builder()
                        .id(platoModel.getRestauranteModel().getId())
                        .build());

        PlatoEntity saved = platoRepository.save(platoEntity);
        return entityMapper.toPlatoModel(saved);

    }



    @Override
    public PlatoModel getPlatoModelById(Long id) {
      Optional<PlatoEntity> filtrarPorId = platoRepository.findById(id);
      return filtrarPorId.map(entityMapper::toPlatoModel).orElseGet(null);
    }

    @Override
    public PlatoModel updatePlatoDescripcionPrecio(Long id, String descripcion, BigDecimal Precio) {
        int filasActualizadas = platoRepository.actualizarDescripcionYPrecio(id, descripcion, Precio);
        return getPlatoModelById(id);
    }

}
