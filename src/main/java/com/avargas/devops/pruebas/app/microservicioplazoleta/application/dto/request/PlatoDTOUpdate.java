package com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatoDTOUpdate {
    @Schema(description = "Precio del plato en números enteros positivos", example = "25000", type = "number", format = "int64")
    private BigDecimal precio;


    @Schema(description = "Descripción del plato", example = "Delicioso arroz con pollo", type = "string")
    private String descripcion;
}
