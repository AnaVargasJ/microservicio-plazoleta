package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.platos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PlatoEntity;
import java.util.List;


public interface IPlatoEntityMapper {

    PlatoEntity toPlatoEntity(PlatoModel plato);
    PlatoModel toPlatoModel(PlatoEntity platoEntity);
    List<PlatoModel> toPlatoModelList(List<PlatoEntity> platoEntityList);
}

