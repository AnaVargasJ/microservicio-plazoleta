package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.pedido;

import jakarta.servlet.http.HttpServletRequest;

public interface INotificacionServicePort {
    
    Boolean notificarUsuario(HttpServletRequest request, Long idUsuario, String mensaje);
}
