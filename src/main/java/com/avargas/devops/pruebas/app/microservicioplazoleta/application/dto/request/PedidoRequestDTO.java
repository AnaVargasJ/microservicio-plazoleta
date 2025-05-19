package com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.util.swagger.SwaggerPedidoDescriptions;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = SwaggerPedidoDescriptions.PEDIDO_REQUEST)
public class PedidoRequestDTO implements Serializable {

    @Schema(description = SwaggerPedidoDescriptions.ID_CLIENTE, example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idCliente;

    @Schema(description = SwaggerPedidoDescriptions.ID_RESTAURANTE, example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idRestaurante;

    @Schema(description = SwaggerPedidoDescriptions.PLATOS, requiredMode = Schema.RequiredMode.REQUIRED)
    private List<PlatoCantidadDTO> platos;
}
