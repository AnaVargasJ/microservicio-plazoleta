package com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class PlatoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Nombre del plato. Puede contener números, pero no solo números", example = "Plato 123", type = "string")
    private String nombre;


    @Schema(description = "Precio del plato en números enteros positivos", example = "25000", type = "number", format = "int64")
    private BigDecimal precio;


    @Schema(description = "Descripción del plato", example = "Delicioso arroz con pollo", type = "string")
    private String descripcion;


    @Schema(description = "URL de la imagen del plato", example = "https://miapp.com/img/plato.png", type = "string", format = "uri")
    private String urlImagen;

    @Schema(description = "ID de la categoría a la que pertenece el plato", example = "1", type = "integer", format = "int64")
    private Long idCategoria;


    @Schema(description = "ID del restaurante al que pertenece el plato", example = "5", type = "integer", format = "int64")
    private Long idRestaurante;
}