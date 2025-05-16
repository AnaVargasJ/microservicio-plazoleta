package com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlatoResponseDTO {

    private Long idPlato;

    private String nombre;

    private BigDecimal precio;

    private String descripcion;

    private String urlImagen;


}
