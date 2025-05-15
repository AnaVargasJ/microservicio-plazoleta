package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.plato.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.platos.IPlatoHandler;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.plato.IPlatoController;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/plato")
@RequiredArgsConstructor
@Tag(name = "Plato", description = "Aplicación que crea platos para asociarlo a un restaurante")
public class PlatoController implements IPlatoController {

    private final IPlatoHandler platoService;

    @Override
    @PostMapping("/crearPlato")
    @PreAuthorize("hasRole('ROLE_PROP')")
    @Operation(
            summary = "Crear Plato",
            description = "Permite al propietario de un restaurante crear un nuevo plato y asociarlo a una categoría y restaurante."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Plato creado correctamente",
                    content = @Content(schema = @Schema(implementation = Object.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de validación o datos incorrectos (por ejemplo, categoría o restaurante inexistente)",
                    content = @Content(schema = @Schema(implementation = Object.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token inválido o no enviado",
                    content = @Content(schema = @Schema(implementation = Object.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado: solo el propietario del restaurante puede crear platos",
                    content = @Content(schema = @Schema(implementation = Object.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor al intentar crear el plato",
                    content = @Content(schema = @Schema(implementation = Object.class))
            )
    })
    public ResponseEntity<?> crearPlato(
            HttpServletRequest request,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del plato a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PlatoDTO.class)))
            @RequestBody PlatoDTO platoDTO) {

         platoService.crearPlato(request, platoDTO);
        return new ResponseEntity<>(ResponseUtil.success("Plato creado correctamente")
                , HttpStatus.CREATED);
    }

}