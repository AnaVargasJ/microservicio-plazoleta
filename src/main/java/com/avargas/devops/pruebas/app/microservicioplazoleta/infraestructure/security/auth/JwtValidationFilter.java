package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security.auth;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.commons.RolUsuario;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.exception.TokenInvalidoException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.client.impl.GenericHttpClient;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security.jwt.TokenJwtConfig;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security.model.UsuarioAutenticado;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;

@Slf4j
@Profile("!test")
public class JwtValidationFilter extends BasicAuthenticationFilter {


    private final GenericHttpClient loginClient;
    @Value("${microserviciopropietarios}")
    private  String urlPropietarios;

    public JwtValidationFilter(AuthenticationManager authManager, GenericHttpClient loginClient) {
        super(authManager);
        this.loginClient = loginClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(TokenJwtConfig.HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(TokenJwtConfig.PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(TokenJwtConfig.PREFIX_TOKEN, "");
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            if (isTokenExpired(decodedJWT.getExpiresAt())) {
                reject(response, "El token ha vencido", HttpStatus.UNAUTHORIZED);
                return;
            }

            String correo = decodedJWT.getSubject();
            Map<String, Object> usuario = consultarUsuarioPorCorreo(correo, header);

            Long id = Long.valueOf(usuario.get("idUsuario").toString());
            String rol = ((Map<String, Object>) usuario.get("rol")).get("nombre").toString();

            Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol));

            UsuarioAutenticado usuarioAutenticado = new UsuarioAutenticado(
                    id,
                    correo,
                    null,
                    authorities
            );

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(usuarioAutenticado, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);

        } catch (Exception ex) {
            log.error("Error en validación de token: {}", ex.getMessage());
            reject(response, "Token inválido: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    private Map<String, Object> consultarUsuarioPorCorreo(String correo, String tokenHeader) {
        String url = urlPropietarios + "/buscarPorCorreo/{correo}";
        Map<String, String> headers = Map.of(HttpHeaders.AUTHORIZATION, tokenHeader);

        Map<String, Object> response = loginClient.sendRequest(
                url.replace("{correo}", correo), HttpMethod.GET, null, headers
        );

        if (response == null || ((Number) response.get("codigo")).intValue() != 200) {
            throw new TokenInvalidoException("Error al consultar el usuario con correo: " + correo);
        }

        return (Map<String, Object>) response.get("respuesta");
    }

    private boolean isTokenExpired(Date exp) {
        return exp.before(new Date());
    }

    private void reject(HttpServletResponse response, String mensaje, HttpStatus status) throws IOException {
        ResponseDTO error = ResponseDTO.builder()
                .mensaje(mensaje)
                .codigo(status.value())
                .build();
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(error));
    }
}