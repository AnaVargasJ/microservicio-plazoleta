package com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.util.swagger.SwaggerPedidoDescriptions;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = SwaggerPedidoDescriptions.PLATO_REQUEST)
public class PlatoCantidadDTO implements Serializable {

    @Schema(description = SwaggerPedidoDescriptions.ID_PLATO, example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idPlato;

    @Schema(description = SwaggerPedidoDescriptions.CANTIDAD_PLATO, example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer cantidad;
}

