package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.platos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PlatoModel;

import java.util.Map;

public interface IPlatoServicePort {
    void crearPlato(PlatoModel platoModel);
    PlatoModel getPlatoModelById(Long id);

    void modificarPlato(Long id, PlatoModel platoModel);

    void activarDesactivarPlato(Long id, Boolean activo, Long idPropietario);






}
