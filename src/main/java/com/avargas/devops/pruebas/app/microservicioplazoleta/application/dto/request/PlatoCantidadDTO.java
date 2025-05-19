package com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatoCantidadDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idPlato;
    private Integer cantidad;
}
