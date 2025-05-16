package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.platos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PlatoEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.categorias.ICategoriaEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.restaurantes.IRestauranteEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = { ICategoriaEntityMapper.class, IRestauranteEntityMapper.class }
)
public interface IPlatoEntityMapper {

    PlatoEntity toPlatoEntity(PlatoModel plato);
    PlatoModel toPlatoModel(PlatoEntity platoEntity);
    List<PlatoModel> toPlatoModelList(List<PlatoEntity> platoEntityList);
}

