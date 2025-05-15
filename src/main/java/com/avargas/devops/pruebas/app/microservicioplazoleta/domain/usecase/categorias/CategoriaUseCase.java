package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.categorias;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.categorias.ICategoriaServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.categorias.CategoriaDataException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.CategoriaModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.categorias.CategoriaPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoriaUseCase implements ICategoriaServicePort {

    private  final CategoriaPersistencePort categoriaPersistencePort;

    @Override
    public CategoriaModel getCategoriaModelById(Long id) {
        CategoriaModel categoriaModel = categoriaPersistencePort.getCategoriaModelById(id);
        if(categoriaModel == null){
            throw new CategoriaDataException("No existe la categoria con el" + id);
        }
        return categoriaModel;
    }

}
