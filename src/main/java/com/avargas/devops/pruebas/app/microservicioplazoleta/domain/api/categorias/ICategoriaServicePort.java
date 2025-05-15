package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.categorias;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.CategoriaModel;

public interface ICategoriaServicePort {
    CategoriaModel getCategoriaModelById(Long id);
}
