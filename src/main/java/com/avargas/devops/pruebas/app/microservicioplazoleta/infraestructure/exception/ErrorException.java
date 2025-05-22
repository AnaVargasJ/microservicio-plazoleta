package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorException {

    DATA_ERROR("No se pudo obtener la información del usuario con el id: "),
    RESPONSE_ERROR("La respuesta del servicio de usuarios es inválida "),
    ERROR_DATA_TYPE("Error en el tipo de dato del celular");

    private final String message;


}
