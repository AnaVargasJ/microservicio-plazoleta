package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "platos")
public class PlatoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    @ToString.Exclude
    private CategoriaEntity categoriaEntity;

    private String descripcion;
    private BigDecimal precio;

    @ManyToOne
    @JoinColumn(name = "id_restaurante", nullable = false)
    @ToString.Exclude
    private RestauranteEntity restauranteEntity;

    private String urlImagen;

    private Boolean activo;
}
