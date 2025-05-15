package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.platos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PlatoModel;


public interface PlatoPersistencePort {
    PlatoModel guardar(PlatoModel platoModel);
    PlatoModel getPlatoModelById(Long id);

}
