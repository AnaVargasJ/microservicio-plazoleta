package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.controllerAdvisor.restaurantes;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.restaurante.ValidacionNegocioException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.exception.RolNoAutorizadoException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class RestauranteExceptionHandlerAdvice {

    @ExceptionHandler(ValidacionNegocioException.class)
    public ResponseEntity<ResponseDTO> handleValidacionNegocioException(ValidacionNegocioException ex) {
        log.error("Excepción de validación de negocio: {}", ex.getMessage());
        ResponseDTO response = ResponseUtil.error(
                "Error de validación de negocio",
                Map.of("error", ex.getMessage()),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleGenericException(Exception ex) {
        log.error("Excepción inesperada: {}", ex.getMessage(), ex);
        ResponseDTO response = ResponseUtil.error(
                "Error interno del servidor",
                Map.of("error", ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RolNoAutorizadoException.class)
    public ResponseEntity<ResponseDTO> handleRolNoAutorizadoException(RolNoAutorizadoException ex) {
        log.warn("Rol no autorizado: {}", ex.getMessage());
        ResponseDTO response = ResponseUtil.error(
                "El correo no pertenece a un usuario con rol propietario.",
                Map.of("error", ex.getMessage()),
                HttpStatus.FORBIDDEN.value()
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("Violación de integridad de datos: {}", ex.getMessage());

        String mensaje = "Error al guardar el plato: hay campos obligatorios que no fueron proporcionados (por ejemplo, categoría o restaurante).";

        ResponseDTO response = ResponseUtil.error(
                mensaje,
                Map.of("error", ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage()),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}