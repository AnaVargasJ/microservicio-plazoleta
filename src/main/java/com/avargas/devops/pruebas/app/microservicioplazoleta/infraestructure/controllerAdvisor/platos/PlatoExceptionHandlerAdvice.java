package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.controllerAdvisor.platos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.platos.PlatoInvalidoException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
@ControllerAdvice
public class PlatoExceptionHandlerAdvice {
    private static final Logger log = LoggerFactory.getLogger(PlatoExceptionHandlerAdvice.class);

    @ExceptionHandler(PlatoInvalidoException.class)
    public ResponseEntity<ResponseDTO> handlePlatoInvalidoException(PlatoInvalidoException ex) {
        log.warn("Validación de plato fallida: {}", ex.getMessage());
        ResponseDTO response = ResponseUtil.error(
                "Datos inválidos para crear el plato.",
                Map.of("error", ex.getMessage()),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
