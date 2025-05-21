package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.pedido.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PedidoRequestDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.pedido.IPedidoHandler;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.input.rest.pedido.IPedidoController;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security.model.UsuarioAutenticado;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.SwaggerConstants.*;

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
                                         @Parameter(description = SwaggerConstants.DESC_PEDIDO_REGISTRAR, required = true,
                                                 content = @Content(schema = @Schema(implementation = PedidoRequestDTO.class)))
                                         @RequestBody PedidoRequestDTO pedidoRequestDTO) {
        pedidoHandler.crearPedidos(pedidoRequestDTO);
        return new ResponseEntity<>(ResponseUtil.success(SwaggerMessagesConstants.PEDIDO_CREADO), HttpStatus.CREATED);
    }

    @Override
    @GetMapping(EndpointApi.LIST_PEDIDOS_BY_ESTADO)
    @PreAuthorize("hasRole('ROLE_EMP')")
    @Operation(
            summary = SwaggerConstants.OP_LISTA_PEDIDO_SUMMARY,
            description = SwaggerConstants.OP_LISTA_PEDIDO_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponseCode.OK, description = SwaggerConstants.RESPONSE_200_DESC, content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = SwaggerResponseCode.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC, content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = SwaggerResponseCode.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC, content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> obtenerListaPedidosPorEstado(@PathVariable("estado") String estado,
                                                          @PathVariable("idRestaurante") Long idRestaurante,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @Parameter(hidden = true)
                                                          @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado) {
        ;

        return ResponseEntity.ok(
                ResponseUtil.success(SwaggerMessagesConstants.PEDIDO_LISTAS_ESTADO + estado + SwaggerMessagesConstants.PEDIDO_LISTAS_RESTAURANTE + idRestaurante,
                        pedidoHandler.obtenerListaPedidosPorEstado(estado, idRestaurante, page, size, usuarioAutenticado.getId())));
    }

    @Override
    @PutMapping(EndpointApi.ASIGNAR_PEDIDOS)
    @Operation(summary = SwaggerConstants.OP_ASIGNAR_PEDIDO_SUMMARY,
            description = SwaggerConstants.OP_ASIGNAR_PEDIDO_DESC)
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponseCode.OK, description = SwaggerConstants.RESPONSE_200_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    @PreAuthorize("hasRole('ROLE_EMP')")
    public ResponseEntity<?> asignarPedido(HttpServletRequest request,
                                           @Parameter(description = DESC_ID_PEDIDO, required = true)
                                           @PathVariable("id") Long idPedido,
                                           @Parameter(description = DESC_ESTADO_PEDIDO, required = true)
                                           @PathVariable String estado,
                                           @Parameter(hidden = true)
                                           @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
    ) {

        pedidoHandler.asignarPedido(request, idPedido, estado, usuarioAutenticado.getId());
        return new ResponseEntity<>(
                ResponseUtil.response(
                        SwaggerMessagesConstants.PEDIDO_USUARIO_CORREO + usuarioAutenticado.getUsername(),
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );

    }

    @Override
    @PutMapping(EndpointApi.ENTREGAR_PEDIDO)
    @Operation(summary = SwaggerConstants.OP_ENTREGAR_PEDIDO_SUMMARY,
            description = SwaggerConstants.OP_ENTREGAR_PEDIDO_DESC)
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponseCode.OK, description = SwaggerConstants.RESPONSE_200_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    @PreAuthorize("hasRole('ROLE_EMP')")
    public ResponseEntity<?> entregarPedido(HttpServletRequest request,
                                            @Parameter(description = DESC_ID_PEDIDO, required = true)
                                            @PathVariable("id") Long idPedido,
                                            @Parameter(description = DESC_ESTADO_PEDIDO, required = true, example = "ENTREGADO")
                                            @PathVariable String estado,
                                            @Parameter(description = DESC_PIN_PEDIDO, required = true)
                                            @PathVariable String pin,
                                            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado) {
        pedidoHandler.asignarPedidoPin( idPedido, estado,  usuarioAutenticado.getId(), pin);
        return new ResponseEntity<>(
                ResponseUtil.response(
                        SwaggerMessagesConstants.ENTREGADO_USUARIO,
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

    @Override
    @PutMapping(EndpointApi.CANCELAR_PEDIDOS)
    @Operation(summary = SwaggerConstants.OP_CANCELAR_PEDIDO_SUMMARY,
            description = SwaggerConstants.OP_CANCELAR_PEDIDO_DESC)
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponseCode.OK, description = SwaggerConstants.RESPONSE_200_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC),
            @ApiResponse(responseCode = SwaggerResponseCode.INTERNAL_SERVER_ERROR, description = SwaggerConstants.RESPONSE_500_DESC)
    })
    @PreAuthorize("hasRole('ROLE_CLI')")
    public ResponseEntity<?> cancelarPedido(HttpServletRequest request,
                                            @Parameter(description = DESC_ID_PEDIDO, required = true)
                                            @PathVariable("id") Long idPedido, @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado) {
        pedidoHandler.cancelarPedido(idPedido, usuarioAutenticado.getId());
        return new ResponseEntity<>(
                ResponseUtil.response(
                        SwaggerMessagesConstants.CANCELAR_PEDIDO,
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK
        );
    }

}

