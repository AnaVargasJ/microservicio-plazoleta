package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.plato.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.platos.IPlatoHandler;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.plato.IPlatoController;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security.model.UsuarioAutenticado;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro plato asociado al restaurante",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token inválido o no enviado",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado: solo el propietario del restaurante puede crear platos",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor al intentar modificar el plato",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            )
    })
    public ResponseEntity<?> modificarPlato(HttpServletRequest request,@PathVariable("idPlato") Long id,
                                            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                    description = "Datos del plato a modificar",
                                                    required = true,
                                                    content = @Content(schema = @Schema(implementation = PlatoDTOUpdate.class)))
                                            @RequestBody PlatoDTOUpdate platoDTO) {

        platoService.modificarPlato(request, id, platoDTO);
        return ResponseEntity.ok(
                ResponseUtil.success("Plato modificado correctamente", Map.of("idPlato", id))
        );
    }

    @Override
    @PatchMapping("/plato/{id}/estado")
    @PreAuthorize("hasRole('PROP')")
    @Operation(summary = "Habilitar o deshabilitar un plato del menú")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado del plato actualizado correctamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado: no es propietario del plato"),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado")
    })
    public ResponseEntity<?> cambiarEstadoPlato(@PathVariable Long id, @RequestParam Boolean activo, @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado)
    {

        platoService.activarDesactivarPlato(id, activo, usuarioAutenticado.getId());

        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .mensaje("Plato " + (activo ? "habilitado" : "deshabilitado") + " correctamente.")
                        .codigo(HttpStatus.OK.value())
                        .build()
        );
    }
}