package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.platos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.PlatoResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IPlatoHandler {
    void crearPlato(HttpServletRequest request, PlatoDTO platoDTO);
    void modificarPlato(HttpServletRequest request, Long id, PlatoDTOUpdate platoDTOUpdate);

    void activarDesactivarPlato(Long id, Boolean activo,Long idPropietario);

    PageResponseDTO<PlatoResponseDTO> listarPlatosRestaurante(Long idRestaurante, Long idCategoria, int page, int size);


}
