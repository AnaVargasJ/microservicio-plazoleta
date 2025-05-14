package com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response;

import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.RestauranteEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link RestauranteEntity}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestauranteResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idRestaurante;
    private String nombre;
    private String direccion;
    private Long idPropietario;
    private String telefono;
    private String urlLogo;
    private String nit;
}