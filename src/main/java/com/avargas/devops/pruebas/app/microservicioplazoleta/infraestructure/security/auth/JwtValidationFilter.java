package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security.auth;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.commons.RolUsuario;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.exception.TokenInvalidoException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.client.impl.GenericHttpClient;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security.jwt.TokenJwtConfig;
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

@Profile("!test")
@Slf4j
public class JwtValidationFilter extends BasicAuthenticationFilter {

    private final GenericHttpClient loginClient;

    @Value("${default.password.admin}")
    private String passwordAdmin;

    @Value("${default.password.prop}")
    private String passwordProp;

    @Value("${default.password.emp}")
    private String passwordCli;

    @Value("${default.password.cli}")
    private String passwordEmp;

    @Value("${microserviciopropietarios}")
    private String urlPropietarios;

    @Value("${microservicioUsuarios}")
    private String urlUsuarios;

    private String password;



    public JwtValidationFilter(AuthenticationManager authenticationManager, GenericHttpClient loginClient) {
        super(authenticationManager);
        this.loginClient = loginClient;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> respuesta = new HashMap<>();
        try {
            String header = request.getHeader(TokenJwtConfig.HEADER_AUTHORIZATION);

            if (header == null || !header.startsWith(TokenJwtConfig.PREFIX_TOKEN)) {
                chain.doFilter(request, response);
                return;
            }

            String token = header.replace(TokenJwtConfig.PREFIX_TOKEN, "");
            DecodedJWT decodedJWT = JWT.decode(token);
            Date exp = decodedJWT.getExpiresAt();

            if (isTokenExpired(exp)) {

                body.put("error", "El token ha vencido");
                response.getWriter().write(new ObjectMapper().writeValueAsString(ResponseUtil.error("El token JWT es incorrecto",
                        body,HttpStatus.UNAUTHORIZED.value())));
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(TokenJwtConfig.CONTENT_TYPE);
                return;
            }

            String username = decodedJWT.getSubject();


            String url = this.urlPropietarios + "/buscarPorCorreo/{correo}";
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            String finalUrl = builder.buildAndExpand(username).toUriString();
            Map<String, String> headersCorreo = Map.of(HttpHeaders.AUTHORIZATION, header);
            respuesta = loginClient.sendRequest(finalUrl, HttpMethod.GET, null, headersCorreo);
            Object codigo = respuesta.get("codigo");

            if (codigo instanceof Number && ((Number) codigo).intValue() != 200) {

                log.error("Error al consultar el usuario por correo");

                body.put("error", "Error al consultar el usuario por correo");

                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(TokenJwtConfig.CONTENT_TYPE);
                response.getWriter().write(new ObjectMapper().writeValueAsString(
                        ResponseUtil.error("Error al consultar el rol del usuario", body, HttpStatus.UNAUTHORIZED.value())
                ));
                return;
            }

            Map<String, Object> respestaRol = (Map<String, Object>) respuesta.get("respuesta");
            Map<String, Object> rol = (Map<String, Object>) respestaRol.get("rol");
            String codigoRol = (String) rol.get("nombre");
            procesarPorRol(codigoRol);


            log.info("Realizando login para el usuario: {}", username);
            String loginUrl = this.urlUsuarios + "/login";

            log.info("Realizando solicitud de login a la URL: {}", loginUrl);
            log.info("Con username: {}, y password: {}", username);


            body.put("correo", username);
            body.put("clave", password);

            Map<String, String> headers = Map.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            respuesta = loginClient.sendRequest(loginUrl, HttpMethod.POST, body, headers);

            log.info("Respuesta del login: {}", respuesta);

            if (respuesta != null && !respuesta.isEmpty()) {

                Object statusCode = respuesta.get("codigo");
                if (statusCode instanceof Integer && (Integer) statusCode == 200) {

                    String authoritiesJson = decodedJWT.getClaim("authorities").asString();
                    Collection<GrantedAuthority> authorities = new ArrayList<>();

                    if (authoritiesJson != null && !authoritiesJson.isEmpty()) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            List<Map<String, String>> parsedAuthorities = objectMapper.readValue(authoritiesJson, List.class);
                            for (Map<String, String> authorityMap : parsedAuthorities) {
                                String role = authorityMap.get("authority");
                                if (role != null) {
                                    authorities.add(new SimpleGrantedAuthority( role));
                                }
                            }
                        } catch (Exception ex) {
                            log.error("Error al parsear los authorities del token JWT: {}", ex.getMessage());
                        }
                    }

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    chain.doFilter(request, response);
                }
                else {
                    throw new TokenInvalidoException("El token no está presente en la respuesta.");
                }

            } else {
                log.error("Error en la validación del token, código de respuesta: {}", respuesta != null && !respuesta.isEmpty() ? respuesta.get("statusCode") : "null");


                respuesta.put("error", "Error al validar el token");

                response.getWriter().write(new ObjectMapper().writeValueAsString(ResponseUtil.error("El token JWT es incorrecto",
                        respuesta,HttpStatus.UNAUTHORIZED.value())));
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(TokenJwtConfig.CONTENT_TYPE);
            }

        } catch (JwtException e) {
            log.error("Error al procesar el token JWT: {}", e.getMessage());

            respuesta.put("error", e.getMessage());

            response.getWriter().write(new ObjectMapper().writeValueAsString(ResponseUtil.error("El token JWT es incorrecto",
                    respuesta,HttpStatus.UNAUTHORIZED.value())));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(TokenJwtConfig.CONTENT_TYPE);
        } catch (Exception e) {
            log.error("Error inesperado al validar el token: {}", e.getMessage());

            respuesta.put("error", "Error al realizar la autenticación: " + e.getMessage());

            response.getWriter().write(new ObjectMapper().writeValueAsString(    ResponseUtil.error("Error al validar el token",
                    respuesta,HttpStatus.INTERNAL_SERVER_ERROR.value())));
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType(TokenJwtConfig.CONTENT_TYPE);
        }
    }

    private Boolean isTokenExpired(Date exp) {
        Date currentDate = new Date();
        return exp.before(currentDate);
    }

    public void procesarPorRol(String rol) {
        try {
            RolUsuario rolUsuario = RolUsuario.valueOf(rol);

            switch (rolUsuario) {
                case ADMIN -> password = passwordAdmin;
                case PROP -> password = passwordProp;
                case EMP -> password = passwordEmp;
                case CLI ->password = passwordCli;
            }

        } catch (IllegalArgumentException e) {
            log.error("Rol inválido: " + rol + " - " + e.getMessage());
            throw new TokenInvalidoException("Rol inválido: " + rol + " - " + e.getMessage());
        }
    }




}
