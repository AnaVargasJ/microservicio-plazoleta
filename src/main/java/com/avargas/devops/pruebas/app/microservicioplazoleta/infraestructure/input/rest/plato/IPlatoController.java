package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.plato;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security.model.UsuarioAutenticado;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface IPlatoController {
    ResponseEntity<?> crearPlato(HttpServletRequest request, PlatoDTO platoDTOt);
    ResponseEntity<?> modificarPlato(HttpServletRequest request,Long id,
                                     PlatoDTOUpdate platoDTO);

    ResponseEntity<?> cambiarEstadoPlato(Long id,  Boolean activo, UsuarioAutenticado usuarioAutenticado
    );
}
