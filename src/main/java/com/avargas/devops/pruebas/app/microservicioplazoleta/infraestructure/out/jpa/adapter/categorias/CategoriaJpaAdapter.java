package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.adapter.categorias;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.CategoriaModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.categorias.CategoriaPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.CategoriaEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.categorias.ICategoriaEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.categorias.CategoriaRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CategoriaJpaAdapter implements CategoriaPersistencePort {

    private final CategoriaRepository categoriaRepository;
    private final ICategoriaEntityMapper iCategoriaEntityMapper;
    @Override
    public CategoriaModel getCategoriaModelById(Long id) {
        Optional<CategoriaEntity> filtrarPorId = categoriaRepository.findById(id);
        return filtrarPorId.map(iCategoriaEntityMapper::toCategoriaModel).orElseGet(null);
    }

}
