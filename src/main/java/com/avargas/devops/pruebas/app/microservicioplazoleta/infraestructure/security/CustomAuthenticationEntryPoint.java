package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        ResponseDTO responseDTO = ResponseUtil.error(
                "Acceso denegado: el token es requerido o inválido.",
                Map.of("error", authException.getMessage()),
                HttpStatus.FORBIDDEN.value()
        );

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(responseDTO));
    }
}