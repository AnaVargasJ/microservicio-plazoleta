package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.platos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import jakarta.servlet.http.HttpServletRequest;

public interface IPlatoHandler {
    void crearPlato(HttpServletRequest request, PlatoDTO platoDTO);
    void modificarPlato(HttpServletRequest request, Long id, PlatoDTOUpdate platoDTOUpdate);

}
