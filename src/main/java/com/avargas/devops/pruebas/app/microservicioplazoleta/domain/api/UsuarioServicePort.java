package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api;

import jakarta.servlet.http.HttpServletRequest;

public interface UsuarioServicePort {

    Boolean usuarioEsPropietario(String correo,  HttpServletRequest request);
}
