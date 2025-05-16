package com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class RestauranteResumenDTO {

    private String nombre;
    private String urlLogo;
}
