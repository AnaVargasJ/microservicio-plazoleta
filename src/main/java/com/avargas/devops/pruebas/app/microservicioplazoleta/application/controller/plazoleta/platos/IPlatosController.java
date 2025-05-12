package com.avargas.devops.pruebas.app.microservicioplazoleta.application.controller.plazoleta.platos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IPlatosController {
    ResponseEntity<?> crearPlato(HttpServletRequest request, PlatoDTO platoDTO, BindingResult result);

}
