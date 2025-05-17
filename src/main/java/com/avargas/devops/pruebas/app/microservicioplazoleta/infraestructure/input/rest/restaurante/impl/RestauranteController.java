package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.restaurante.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.RestauranteDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.restaurante.IRestauranteHandler;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.restaurante.IRestauranteController;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.EndpointApi;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.ResponseUtil;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.SwaggerConstants;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.SwaggerResponseCode;
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
@RequestMapping(EndpointApi.BASE_PATH_RESTAURANTE)
@RequiredArgsConstructor
@Tag(name = SwaggerConstants.TAG_RESTAURANTE, description = SwaggerConstants.TAG_RESTAURANTE_DESC)
public class RestauranteController implements IRestauranteController {

    private final IRestauranteHandler restauranteService;

    @Override
    @PostMapping(EndpointApi.CREATE_RESTAURANTE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = SwaggerConstants.OP_CREAR_RESTAURANTE_SUMMARY,
            description = SwaggerConstants.OP_CREAR_RESTAURANTE_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponseCode.CREATED, description = SwaggerConstants.RESPONSE_201_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC)
    })
    public ResponseEntity<?> crearRestaurante(HttpServletRequest request,
                                              @Parameter(description = "Datos del restaurante", required = true,
                                                      content = @Content(schema = @Schema(implementation = RestauranteDTO.class)))
                                              @RequestBody RestauranteDTO restauranteDTO) {
        restauranteService.crearRestaurante(request, restauranteDTO);
        return new ResponseEntity<>(ResponseUtil.success("Restaurante creado correctamente"), HttpStatus.CREATED);
    }

    @Override
    @GetMapping(EndpointApi.LIST_RESTAURANTES)
    @PreAuthorize("hasRole('ROLE_CLI')")
    @Operation(
            summary = SwaggerConstants.OP_LISTAR_RESTAURANTES_SUMMARY,
            description = SwaggerConstants.OP_LISTAR_RESTAURANTES_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponseCode.OK, description = SwaggerConstants.RESPONSE_200_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })

    public ResponseEntity<?> listarRestaurantes(HttpServletRequest request, int page, int size) {
        return ResponseEntity.ok(
                ResponseUtil.success("Restaurantes listados correctamente",
                        restauranteService.listarRestaurante(page, size))
        );
    }
}
