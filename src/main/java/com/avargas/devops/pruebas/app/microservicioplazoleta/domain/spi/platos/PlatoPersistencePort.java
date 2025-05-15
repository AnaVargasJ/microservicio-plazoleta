package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.platos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PlatoModel;

import java.math.BigDecimal;


public interface PlatoPersistencePort {
    PlatoModel guardar(PlatoModel platoModel);
    PlatoModel getPlatoModelById(Long id);

    PlatoModel updatePlatoDescripcionPrecio(Long id, String descripcion, BigDecimal Precio);


}
