package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.controllerAdvisor;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.pedido.PedidoInvalidoException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

import static com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.controllerAdvisor.pedido.ErrorMessagesConstants.DATOS_INVALIDOS_PEDIDO;
import static com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.controllerAdvisor.pedido.ErrorMessagesConstants.ERROR_GENERAL_GUARDAR_PEDIDO;

@ControllerAdvice
@Slf4j
public class PedidoExceptionHandlerAdvice {



    @ExceptionHandler(PedidoInvalidoException.class)
    public ResponseEntity<ResponseDTO> handlePedidoInvalido(PedidoInvalidoException ex) {
        log.warn("Validación de pedido fallida: {}", ex.getMessage());

        ResponseDTO response = ResponseUtil.error(
                DATOS_INVALIDOS_PEDIDO,
                Map.of("error", ex.getMessage()),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDTO> handleErrorGuardarPedido(DataIntegrityViolationException ex) {
        log.error("Error al guardar pedido: {}", ex.getMessage());

        String detalle = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();

        ResponseDTO response = ResponseUtil.error(
                ERROR_GENERAL_GUARDAR_PEDIDO,
                Map.of("error", detalle),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}