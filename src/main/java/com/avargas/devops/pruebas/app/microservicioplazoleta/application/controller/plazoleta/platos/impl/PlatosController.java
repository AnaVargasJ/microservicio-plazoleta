package com.avargas.devops.pruebas.app.microservicioplazoleta.application.controller.plazoleta.platos.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.controller.plazoleta.platos.IPlatosController;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.platos.impl.PlatoService;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.validation.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/plato")
@RequiredArgsConstructor
@Tag(name = "Plato", description = "Aplicación que crea platos para asociarlo a un restaurante")
public class PlatosController implements IPlatosController {

    private final PlatoService platoService;

    private final ValidationService validationService;

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
                    @Valid @RequestBody PlatoDTO platoDTO, BindingResult result) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        return platoService.crearPlato(request, platoDTO);
    }


    @Override
    @PutMapping("/modificarPlato/{idPlato}")
    @PreAuthorize("hasRole('ROLE_PROP')")
    @Operation(
            summary = "Modificar Plato",
            description = "Permite al propietario de un restaurante crear modificar un plato con la descripcion o precio"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se modificó correctamente el plato",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro plato asociado al restaurante",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token inválido o no enviado",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado: solo el propietario del restaurante puede crear platos",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor al intentar modificar el plato",
                    content = @Content(schema = @Schema(implementation = Map.class))
            )
    })
    public ResponseEntity<?> modificarPlato(HttpServletRequest request,@PathVariable("idPlato") Long id,
                                            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                    description = "Datos del plato a modificar",
                                                    required = true,
                                                    content = @Content(schema = @Schema(implementation = PlatoDTOUpdate.class)))
                                            @Valid @RequestBody PlatoDTOUpdate platoDTO, BindingResult result) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }

        return platoService.modificarPlato(request, id, platoDTO);
    }

}
