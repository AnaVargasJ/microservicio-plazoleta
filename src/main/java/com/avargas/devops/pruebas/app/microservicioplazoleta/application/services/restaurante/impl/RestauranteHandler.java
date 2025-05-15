package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.restaurante.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.RestauranteDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.restaurante.IRestauranteRequestMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.restaurante.IRestauranteHandler;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.restaurante.RestauranteServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.restaurante.UsuarioServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RestauranteHandler implements IRestauranteHandler {

    private final RestauranteServicePort restauranteServicePort;
    private final UsuarioServicePort servicePort;
    private final IRestauranteRequestMapper restauranteRequestMapper;

    @Override
    public void crearRestaurante(HttpServletRequest request, RestauranteDTO restauranteDTO) {
        RestauranteModel restauranteModel = restauranteRequestMapper.toModel(restauranteDTO);

        Long esPropietario = servicePort.usuarioEsPropietario(restauranteDTO.getCorreo(), request);
        restauranteModel.setIdPropietario(esPropietario);
        restauranteServicePort.crearRestaurante(restauranteModel);
    }

}
