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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/plazoleta")
@RequiredArgsConstructor
@Tag(name = "Restaurante", description = "Aplicación que crea restaurantes por medio de un administrador")
public class RestauranteController implements IRestauranteController {

    private final IRestauranteHandler restauranteService;


    @Override
    @PostMapping("/crearRestaurante")
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
                                               @RequestBody RestauranteDTO restauranteDTO,
                                               BindingResult result ) {


         restauranteService.crearRestaurante(request, restauranteDTO);

         return new ResponseEntity<>(ResponseUtil.success("Restaurante creado correctamente")
                 , HttpStatus.CREATED);
    }



}
