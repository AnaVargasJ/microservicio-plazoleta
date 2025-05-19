package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.platos.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PlatoEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.categorias.ICategoriaEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.platos.IPlatoEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.restaurantes.IRestauranteEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PlatoEntityMapper implements IPlatoEntityMapper {

    private final IRestauranteEntityMapper restauranteMapper;
    private final ICategoriaEntityMapper categoriaMapper;
    @Override
    public PlatoEntity toPlatoEntity(PlatoModel plato) {
        if (plato == null) return null;

        return PlatoEntity.builder()
                .id(plato.getId())
                .nombre(plato.getNombre())
                .descripcion(plato.getDescripcion())
                .precio(plato.getPrecio())
                .urlImagen(plato.getUrlImagen())
                .activo(plato.getActivo())
                .restauranteEntity(restauranteMapper.toRestauranteEntity(plato.getRestauranteModel()))
                .categoriaEntity(categoriaMapper.toCategoriaEntity(plato.getCategoriaModel()))
                .build();
    }

    @Override
    public PlatoModel toPlatoModel(PlatoEntity platoEntity) {
        if (platoEntity == null) return null;

        return PlatoModel.builder()
                .id(platoEntity.getId())
                .nombre(platoEntity.getNombre())
                .descripcion(platoEntity.getDescripcion())
                .precio(platoEntity.getPrecio())
                .urlImagen(platoEntity.getUrlImagen())
                .activo(platoEntity.getActivo())
                .restauranteModel(restauranteMapper.toRestauranteModel(platoEntity.getRestauranteEntity()))
                .categoriaModel(categoriaMapper.toCategoriaModel(platoEntity.getCategoriaEntity()))
                .build();
    }

    @Override
    public List<PlatoModel> toPlatoModelList(List<PlatoEntity> platoEntityList) {
        if (platoEntityList == null) return new ArrayList<>();

        return platoEntityList.stream()
                .map(this::toPlatoModel)
                .collect(Collectors.toList());
    }
}
