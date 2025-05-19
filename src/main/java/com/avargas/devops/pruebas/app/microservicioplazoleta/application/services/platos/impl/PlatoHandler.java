package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.platos.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.PlatoResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.plato.IPagePlatoResponseMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.plato.IPlatoRequestMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.plato.IPlatoResponseMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.plato.IPlatoUpdateRequestMapper;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlatoHandler implements IPlatoHandler {

    private final IPlatoServicePort iPlatoServicePort;
    private final RestauranteServicePort restauranteServicePort;
    private final ICategoriaServicePort  iCategoriaServicePort;
    private final IPlatoRequestMapper iPlatoRequestMapper;
    private final IPlatoUpdateRequestMapper iPlatoUpdateRequestMapper;
    private final IPagePlatoResponseMapper iPageResponseMapper;
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

    @Override
    public void modificarPlato(HttpServletRequest request, Long id, PlatoDTOUpdate platoDTOUpdate) {
     PlatoModel platoModel = iPlatoUpdateRequestMapper.toModel(platoDTOUpdate);
     iPlatoServicePort.modificarPlato(id, platoModel);
    }

    @Override
    public void activarDesactivarPlato(Long id, Boolean activo, Long idPropietario) {

        iPlatoServicePort.activarDesactivarPlato(id, activo, idPropietario);
    }

    @Override
    public PageResponseDTO<PlatoResponseDTO> listarPlatosRestaurante(Long idRestaurante, Long idCategoria, int page, int size) {
        return iPageResponseMapper.toResponse(iPlatoServicePort.listarPlatosRestaurante(idRestaurante,idCategoria,page, size));
    }
}
