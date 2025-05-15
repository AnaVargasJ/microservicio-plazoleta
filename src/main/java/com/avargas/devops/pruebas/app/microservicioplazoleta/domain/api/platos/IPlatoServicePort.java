package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.platos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PlatoModel;

public interface IPlatoServicePort {
    void crearPlato(PlatoModel platoModel);
    PlatoModel getPlatoModelById(Long id);

    void modificarPlato(Long id, PlatoModel platoModel);


}
