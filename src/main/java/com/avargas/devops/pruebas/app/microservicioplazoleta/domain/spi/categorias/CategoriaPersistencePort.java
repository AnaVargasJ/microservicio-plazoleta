package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.categorias;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.CategoriaModel;

public interface CategoriaPersistencePort {

    CategoriaModel getCategoriaModelById(Long id);
}
