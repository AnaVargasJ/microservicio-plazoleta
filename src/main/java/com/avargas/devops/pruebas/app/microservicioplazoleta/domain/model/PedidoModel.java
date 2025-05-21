package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoModel {

    private Long id;
    private LocalDateTime fecha;
    private String estado;
    private Long idCliente;
    private Long idChef;
    private String pinSeguridad;
    private RestauranteModel restauranteModel;
    private List<PedidoPlatoModel> platos = new ArrayList<>();
}
