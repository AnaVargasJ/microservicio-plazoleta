package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatoModel {

    private Long id;
    private String nombre;
    private CategoriaModel categoriaModel;
    private String descripcion;
    private BigDecimal precio;
    private RestauranteModel restauranteModel;
    private String urlImagen;
    private Boolean activo;
}
