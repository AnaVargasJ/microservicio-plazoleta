package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoPlatoId {
    private Long pedidoId;
    private Long platoId;

}
