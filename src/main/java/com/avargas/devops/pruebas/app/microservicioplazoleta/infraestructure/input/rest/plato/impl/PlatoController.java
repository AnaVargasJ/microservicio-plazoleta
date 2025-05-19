package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.plato.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.platos.IPlatoHandler;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.plato.IPlatoController;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security.model.UsuarioAutenticado;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.EndpointApi;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.ResponseUtil;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.SwaggerConstants;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.SwaggerResponseCode;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(EndpointApi.BASE_PATH_PLATOS)
@RequiredArgsConstructor
@Tag(name = SwaggerConstants.TAG_PLATO, description = SwaggerConstants.TAG_PLATO_DESC)
public class PlatoController implements IPlatoController {

    private final IPlatoHandler platoService;

    @Override
    @PostMapping(EndpointApi.CREATE_PLATOS)
    @PreAuthorize("hasRole('ROLE_PROP')")
    @Operation(
            summary = SwaggerConstants.OP_CREAR_PLATO_SUMMARY,
            description = SwaggerConstants.OP_CREAR_PLATO_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponseCode.CREATED, description = SwaggerConstants.RESPONSE_201_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    public ResponseEntity<?> crearPlato(
            HttpServletRequest request,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del plato a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PlatoDTO.class)))
            @RequestBody PlatoDTO platoDTO) {

        platoService.crearPlato(request, platoDTO);
        return new ResponseEntity<>(ResponseUtil.success("Plato creado correctamente"), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(EndpointApi.UPDATE_PLATO)
    @PreAuthorize("hasRole('ROLE_PROP')")
    @Operation(
            summary = SwaggerConstants.OP_MODIFICAR_PLATO_SUMMARY,
            description = SwaggerConstants.OP_MODIFICAR_PLATO_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponseCode.OK, description = SwaggerConstants.RESPONSE_200_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.NOT_FOUND, description = SwaggerConstants.RESPONSE_404_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    public ResponseEntity<?> modificarPlato(HttpServletRequest request, @PathVariable("idPlato") Long id,
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
    @PatchMapping(EndpointApi.DISABLE_PLATO)
    @PreAuthorize("hasRole('ROLE_PROP')")
    @Operation(summary = SwaggerConstants.OP_CAMBIAR_ESTADO_PLATO_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponseCode.OK, description = SwaggerConstants.RESPONSE_200_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.NOT_FOUND, description = SwaggerConstants.RESPONSE_404_DESC)
    })
    public ResponseEntity<?> cambiarEstadoPlato(@PathVariable Long id, @RequestParam Boolean activo, @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado) {
        platoService.activarDesactivarPlato(id, activo, usuarioAutenticado.getId());

        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .mensaje("Plato " + (activo ? "habilitado" : "deshabilitado") + " correctamente.")
                        .codigo(HttpStatus.OK.value())
                        .build()
        );
    }

    @Override
    @GetMapping(EndpointApi.LIST_PLATOS)
    @PreAuthorize("hasRole('ROLE_CLI')")
    @Operation(
            summary = SwaggerConstants.OP_LISTAR_PLATOS_SUMMARY,
            description = SwaggerConstants.OP_LISTAR_PLATOS_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponseCode.OK, description = SwaggerConstants.RESPONSE_200_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    public ResponseEntity<?> listarPlatosRestaurante(HttpServletRequest request, @PathVariable Long idRestaurante,
                                                     @RequestParam(required = false) Long idCategoria,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                ResponseUtil.success("Se listan los platos del restaurante con el id " + idRestaurante,
                        platoService.listarPlatosRestaurante(idRestaurante, idCategoria, page, size))
        );
    }
}
