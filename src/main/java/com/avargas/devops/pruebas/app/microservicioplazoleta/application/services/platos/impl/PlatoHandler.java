package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.platos.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.plato.IPlatoRequestMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.platos.IPlatoHandler;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.categorias.ICategoriaServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.platos.IPlatoServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.restaurante.RestauranteServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.CategoriaModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatoHandler implements IPlatoHandler {

    private final IPlatoServicePort iPlatoServicePort;
    private final RestauranteServicePort restauranteServicePort;
    private final ICategoriaServicePort  iCategoriaServicePort;
    private final IPlatoRequestMapper iPlatoRequestMapper;
    @Override
    public void crearPlato(HttpServletRequest request, PlatoDTO platoDTO) {
        PlatoModel platoModel = iPlatoRequestMapper.toModel(platoDTO);
        RestauranteModel restauranteModel = restauranteServicePort.getRestauranteModelById(platoDTO.getIdRestaurante());
        CategoriaModel categoriaModel = iCategoriaServicePort.getCategoriaModelById(platoDTO.getIdCategoria());
        platoModel.setCategoriaModel(categoriaModel);
        platoModel.setActivo(Boolean.TRUE);
        platoModel.setRestauranteModel(restauranteModel);
        iPlatoServicePort.crearPlato(platoModel);
    }
}
