package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.restaurante.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.repositories.restaurante.RestauranteRepository;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.restaurante.IRestauranteService;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.commons.domains.generic.RestauranteDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.client.GenericHttpClient;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestauranteService implements IRestauranteService {

    private final GenericHttpClient genericHttpClient;

    private final RestauranteRepository restauranteRepository;

    @Value("${urlUsuarios}")
    private String urlUsuarios;
    @Override
    public ResponseEntity<?> crearRestaurante(HttpServletRequest request, RestauranteDTO restauranteDTO) {
        String token = "Bearer " + request.getHeader("Authorization");
        String url = this.urlUsuarios + "/buscarPorCorreo/{correo}";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        String finalUrl = builder.buildAndExpand(restauranteDTO.getCorreo()).toUriString();
        Map<String, String> headers = Map.of(HttpHeaders.AUTHORIZATION, token);
        Map<String, Object> response = genericHttpClient.sendRequest(finalUrl, HttpMethod.GET, null, headers);
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }
}
