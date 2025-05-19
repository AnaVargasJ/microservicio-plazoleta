package com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idCliente;
    private Long idRestaurante;
    private List<PlatoCantidadDTO> platos;
}
