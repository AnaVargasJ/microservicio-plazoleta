package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "restaurantes")
public class RestauranteEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Column(name = "id_propietario", nullable = false)
    private Long idPropietario;

    private String telefono;
    private String urlLogo;
    private String nit;

    @OneToMany(mappedBy = "restauranteEntity", cascade = CascadeType.ALL)
    private List<PlatoEntity> platoEntities = new ArrayList<>();
}