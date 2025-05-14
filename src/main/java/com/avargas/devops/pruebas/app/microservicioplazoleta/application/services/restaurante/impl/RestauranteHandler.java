package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.restaurante.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.RestauranteDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.IRestauranteRequestMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.restaurante.IRestauranteHandler;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.RestauranteServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.UsuarioServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.exception.RolNoAutorizadoException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;

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

        Boolean esPropietario = servicePort.usuarioEsPropietario(restauranteDTO.getCorreo(), request);
        if (!esPropietario) {
            throw new RolNoAutorizadoException("Rol no autorizado");
        }

        restauranteServicePort.crearRestaurante(restauranteModel);
    }



}
