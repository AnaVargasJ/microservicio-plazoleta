package com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.validator.ExistsCategoriaId;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.validator.ExistsRestauranteId;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.FieldIgnore;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.FieldMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
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

    @FieldMapping("id")
    private Long idPlato;

    @NotBlank(message = "El nombre del plato es obligatorio")
    @Pattern(regexp = "^(?!\\d+$).*$", message = "El nombre no puede ser solo números")
    @Schema(description = "Nombre del plato. Puede contener números, pero no solo números", example = "Plato 123", type = "string")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser un número positivo mayor a 0")
    @Schema(description = "Precio del plato en números enteros positivos", example = "25000", type = "number", format = "int64")
    private BigDecimal precio;

    @NotBlank(message = "La descripción es obligatoria")
    @Schema(description = "Descripción del plato", example = "Delicioso arroz con pollo", type = "string")
    private String descripcion;

    @NotBlank(message = "La URL de la imagen es obligatoria")
    @Schema(description = "URL de la imagen del plato", example = "https://miapp.com/img/plato.png", type = "string", format = "uri")
    private String urlImagen;

    @FieldIgnore
    @ExistsCategoriaId(message = "La categoría no existe")
    @NotNull(message = "La categoría es obligatoria")
    @Schema(description = "ID de la categoría a la que pertenece el plato", example = "1", type = "integer", format = "int64")
    private Long idCategoria;

    @FieldIgnore
    @ExistsRestauranteId(message = "El restaurante no existe")
    @NotNull(message = "El ID del restaurante es obligatorio")
    @Schema(description = "ID del restaurante al que pertenece el plato", example = "5", type = "integer", format = "int64")
    private Long idRestaurante;
}
