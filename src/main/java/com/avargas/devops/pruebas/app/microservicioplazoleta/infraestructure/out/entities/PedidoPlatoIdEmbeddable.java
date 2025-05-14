package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoPlatoIdEmbeddable implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long pedidoId;
    private Long platoId;
}
