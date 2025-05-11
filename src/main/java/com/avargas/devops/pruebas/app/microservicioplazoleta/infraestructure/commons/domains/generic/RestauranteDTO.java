package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.commons.domains.generic;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestauranteDTO implements Serializable {

    private static final long serialVersionUID = 1L;



    @NotBlank
    @Pattern(regexp = "^(?!\\d+$).*$", message = "El nombre no puede ser solo números")
    @Schema(description = "Nombre del restaurante. Puede contener números, pero no solo números", example = "Restaurante 123", type = "string")
    private String nombre;

    @NotBlank
    @Schema(description = "Dirección del restaurante", example = "Calle 123 #45-67", type = "string")
    private String direccion;

    @NotBlank
    @Email
    @Schema(description = "Correo electrónico del usuario", example = "usuario@correo.com", type = "string", format = "email")
    private String correo;

    @NotBlank
    @Pattern(regexp = "^[0-9]+$", message = "El NIT debe ser numérico")
    @Schema(description = "NIT del restaurante, solo números", example = "9012345678", type = "string")
    private String nit;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{1,13}$", message = "El teléfono debe ser numérico, máximo 13 caracteres y puede iniciar con +")
    @Schema(description = "Teléfono del restaurante, puede iniciar con +, máximo 13 caracteres", example = "+573005698325", type = "string", maxLength = 13)
    private String telefono;

    @NotBlank
    @Schema(description = "URL del logo del restaurante", example = "https://miapp.com/logo.png", type = "string", format = "uri")
    private String urlLogo;
}
