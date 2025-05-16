package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.restaurante.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.restaurante.IRestauranteController;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.restaurante.IRestauranteHandler;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.RestauranteDTO;
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
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/plazoleta")
@RequiredArgsConstructor
@Tag(name = "Restaurante", description = "Aplicación que crea restaurantes por medio de un administrador")
public class RestauranteController implements IRestauranteController {

    private final IRestauranteHandler restauranteService;

    @Override
    @PostMapping("/crearRestaurante")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Crear Restaurante",
            description = "Crear un nuevo restaurante en la base de datos con el rol usuario Administrador")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Restaurante creado correctamente",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            )
            , @ApiResponse(
            responseCode = "400",
            description = "Error al crear el restaurante: Rol no encontrado",
            content = @Content(schema = @Schema(implementation = ResponseDTO.class))
    ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token invalido",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado: No tiene permisos para realizar esta operación",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            )
    })
    public ResponseEntity<?> crearRestaurante(HttpServletRequest request,
                                              @Parameter(description = "Datos del restaurante", required = true, content = @Content(schema = @Schema(implementation = RestauranteDTO.class)))
                                               @RequestBody RestauranteDTO restauranteDTO) {
         restauranteService.crearRestaurante(request, restauranteDTO);
         return new ResponseEntity<>(ResponseUtil.success("Restaurante creado correctamente"),
                 HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLI')")
    @Operation(
            summary = "Listar restaurantes disponibles",
            description = "Retorna un listado paginado y ordenado alfabéticamente de restaurantes con su nombre y URL de logo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de restaurantes obtenida correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)
    })
    @GetMapping("/restaurantes")
    public ResponseEntity<?> listarRestaurantes(HttpServletRequest request,int page, int size) {

        return ResponseEntity.ok(
                ResponseUtil.success("Restaurantes listados correctamente", restauranteService.listarRestaurante( page, size)));
    }
}
