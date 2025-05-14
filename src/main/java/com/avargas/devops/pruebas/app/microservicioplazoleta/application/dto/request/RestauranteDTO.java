package com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Nombre del usuario. Puede contener números, pero no solo números", example = "Juan 123")
    private String nombre;


    @Schema(description = "Dirección del restaurante", example = "Calle 123 #45-67", type = "string")
    private String direccion;


    @Schema(description = "Correo electrónico del usuario", example = "usuario@correo.com", type = "string", format = "email")
    private String correo;

    @Schema(description = "NIT del restaurante, solo números", example = "9012345678", type = "string")
    private String nit;


    @Schema(description = "Teléfono del restaurante, puede iniciar con +, máximo 13 caracteres",
            example = "+573005698325",
            type = "string",
            maxLength = 13,
            pattern = "^\\+?[0-9]{1,13}$")
    private String telefono;


    @Schema(description = "URL del logo del restaurante", example = "https://miapp.com/logo.png", type = "string", format = "uri")
    private String urlLogo;
}
