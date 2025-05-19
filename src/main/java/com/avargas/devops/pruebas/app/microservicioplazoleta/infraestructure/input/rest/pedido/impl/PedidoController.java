package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.pedido.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PedidoRequestDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.pedido.IPedidoHandler;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.pedido.IPedidoController;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.*;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointApi.BASE_PATH_PEDIDOS)
@RequiredArgsConstructor
@Tag(name = SwaggerConstants.TAG_PEDIDO, description = SwaggerConstants.TAG_PEDIDO_DESC)
public class PedidoController implements IPedidoController {

    private final IPedidoHandler pedidoHandler;
    @Override
    @PostMapping(EndpointApi.CREATE_PEDIDOS)
    @Operation(summary = SwaggerConstants.OP_CREAR_PEDIDO_SUMMARY,
            description = SwaggerConstants.OP_CREAR_PEDIDO_DESC)
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponseCode.CREATED, description = SwaggerConstants.RESPONSE_201_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    @PreAuthorize("hasRole('ROLE_CLI')")
    public ResponseEntity<?> crearPedido(HttpServletRequest request,
                                                    @Parameter(description = SwaggerConstants.DESC_PEDIDO_REGISTRAR , required = true,
                                                            content = @Content(schema = @Schema(implementation = PedidoRequestDTO.class)))
                                                    @RequestBody PedidoRequestDTO pedidoRequestDTO)  {
         pedidoHandler.crearPedidos(pedidoRequestDTO);
        return new ResponseEntity<>(ResponseUtil.success(SwaggerMessagesConstants.PEDIDO_CREADO), HttpStatus.CREATED);
    }
}
