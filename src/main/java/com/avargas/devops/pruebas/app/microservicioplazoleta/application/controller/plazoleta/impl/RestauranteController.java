package com.avargas.devops.pruebas.app.microservicioplazoleta.application.controller.plazoleta;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/plazoleta")
@RequiredArgsConstructor
public class HolaMundoController {

    @RequestMapping("/hola")
    @PreAuthorize("hasRole('ADMIN')")
    public String holaMundo() {
        return "Hola Mundo desde el microservicio de Plazoleta";
    }
}
