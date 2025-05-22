package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.restaurante;

import jakarta.servlet.http.HttpServletRequest;

public interface UsuarioServicePort {

    Long usuarioEsPropietario(String correo,  HttpServletRequest request);

    String obtenerCorreo(Long idUsuario,  String token);
}
