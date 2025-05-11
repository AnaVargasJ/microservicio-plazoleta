package com.avargas.devops.pruebas.app.microservicioplazoleta.model.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pedidos_platos")
public class PedidoPlato implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PedidoPlatoId id = new PedidoPlatoId();

    @ManyToOne
    @MapsId("pedidoId")
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @MapsId("platoId")
    @JoinColumn(name = "id_plato")
    private Plato plato;

    @Column(nullable = false)
    private Integer cantidad;
}
