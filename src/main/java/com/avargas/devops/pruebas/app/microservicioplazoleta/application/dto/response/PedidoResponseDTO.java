package com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoResponseDTO implements Serializable {
    private Long idPedido;
    private String fecha;
    private String estado;
    private Long idCliente;
    private Long idChef;
}