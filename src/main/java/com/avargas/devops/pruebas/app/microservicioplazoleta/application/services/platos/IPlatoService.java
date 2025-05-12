package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.platos;


import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IPlatoService {

    ResponseEntity<?> crearPlato(HttpServletRequest request, PlatoDTO  platoDTO);

    ResponseEntity<?> modificarPlato(HttpServletRequest request, Long id,
                                     PlatoDTOUpdate platoDTO);

}
