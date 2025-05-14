package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoPlatoModel {

    private PedidoPlatoId id;
    private PedidoModel pedidoModel;
    private PlatoModel platoModel;
    private Integer cantidad;
}
