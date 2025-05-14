package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities;


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
public class PedidoPlatoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PedidoPlatoIdEmbeddable id = new PedidoPlatoIdEmbeddable();

    @ManyToOne
    @MapsId("pedidoId")
    @JoinColumn(name = "id_pedido")
    private PedidoEntity pedidoEntity;

    @ManyToOne
    @MapsId("platoId")
    @JoinColumn(name = "id_plato")
    private PlatoEntity platoEntity;

    @Column(nullable = false)
    private Integer cantidad;
}
