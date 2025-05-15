package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.platos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface IPlatoHandler {

    void crearPlato(HttpServletRequest request, PlatoDTO platoDTO);

}
