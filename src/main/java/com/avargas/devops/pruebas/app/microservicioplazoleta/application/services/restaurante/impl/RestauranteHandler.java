package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.restaurante.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.RestauranteDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.RestauranteResumenDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.restaurante.IRestauranteRequestMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.restaurante.IRestauranteResponseMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.restaurante.IRestauranteHandler;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.restaurante.RestauranteServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.restaurante.UsuarioServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RestauranteHandler implements IRestauranteHandler {

    private final RestauranteServicePort restauranteServicePort;
    private final UsuarioServicePort servicePort;
    private final IRestauranteRequestMapper restauranteRequestMapper;
    private final IRestauranteResponseMapper iRestauranteResponseMapper;

    @Override
    public void crearRestaurante(HttpServletRequest request, RestauranteDTO restauranteDTO) {
        RestauranteModel restauranteModel = restauranteRequestMapper.toModel(restauranteDTO);

        Long esPropietario = servicePort.usuarioEsPropietario(restauranteDTO.getCorreo(), request);
        restauranteModel.setIdPropietario(esPropietario);
        restauranteServicePort.crearRestaurante(restauranteModel);
    }

    @Override
    public List<RestauranteResumenDTO> listarRestaurante(int page, int size) {
        List<RestauranteModel> modelos = restauranteServicePort
                .listarRestaurantesPaginados(page, size)
                .getContent();
        List<RestauranteResumenDTO> dtos = iRestauranteResponseMapper
                .toResponseRestaurantesList(modelos);
        return  dtos;
    }

}
