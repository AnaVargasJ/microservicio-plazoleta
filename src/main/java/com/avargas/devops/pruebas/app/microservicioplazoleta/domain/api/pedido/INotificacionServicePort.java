package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.pedido;

import jakarta.servlet.http.HttpServletRequest;

public interface INotificacionServicePort {
    
    Boolean notificarUsuario(String token, Long idUsuario, String mensaje);
}
