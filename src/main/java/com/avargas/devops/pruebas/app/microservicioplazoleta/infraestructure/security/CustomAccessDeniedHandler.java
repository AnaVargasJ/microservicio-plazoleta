package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security;

import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Acceso denegado: No tiene permisos para realizar esta operación");
        errorResponse.put("ruta", request.getRequestURI());


        response.getWriter().write(new ObjectMapper().writeValueAsString(ResponseUtil.error("Acceso denegado: No tiene permisos para realizar esta operación",
                errorResponse,
                HttpStatus.FORBIDDEN.value())));
    }
}
