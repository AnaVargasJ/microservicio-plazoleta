package com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatoDTOUpdate implements Serializable {

    private static final long serialVersionUID = 1L;


    @Positive(message = "El precio debe ser un número positivo mayor a 0")
    @Schema(description = "Precio del plato en números enteros positivos", example = "25000", type = "number", format = "int64")
    private BigDecimal precio;

    @Schema(description = "Descripción del plato", example = "Delicioso arroz con pollo", type = "string")
    private String descripcion;

}