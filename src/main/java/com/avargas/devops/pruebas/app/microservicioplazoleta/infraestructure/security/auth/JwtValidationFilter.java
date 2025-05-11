package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security.auth;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.client.GenericHttpClient;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security.jwt.SimpleGrantedAuthorityJsonCreator;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security.jwt.TokenJwtConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.*;

@Slf4j
public class JwtValidationFilter extends BasicAuthenticationFilter {

    private final GenericHttpClient loginClient;

    @Value("${default.password}")
    private String password;

    @Value("${urlUsuarios}")
    private String url;



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
                body.put("mensaje", "El token JWT es incorrecto");
                body.put("status", HttpStatus.UNAUTHORIZED.value());



                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(TokenJwtConfig.CONTENT_TYPE);
                return;
            }

            String username = decodedJWT.getSubject();
            log.info("Realizando login para el usuario: {}", username);
            String loginUrl = url + "/login";

            log.info("Realizando solicitud de login a la URL: {}", loginUrl);
            log.info("Con username: {}, y password: {}", username, password);


            body.put("correo", username);
            body.put("clave", password);

            Map<String, String> headers = Map.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);



            respuesta = loginClient.sendRequest(loginUrl, HttpMethod.POST, body, headers);

            log.info("Respuesta del login: {}", respuesta);

            if (respuesta != null && !respuesta.isEmpty()) {

                Object statusCode = respuesta.get("codigo");
                if (statusCode instanceof Integer && (Integer) statusCode == 200) {
                    // Obtener roles del JWT
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
                    throw new RuntimeException("El token no está presente en la respuesta.");
                }

            } else {
                log.error("Error en la validación del token, código de respuesta: {}", respuesta != null && !respuesta.isEmpty() ? respuesta.get("statusCode") : "null");


                respuesta.put("error", "Error al validar el token");
                respuesta.put("mensaje","El token JWT es incorrecto");
                respuesta.put("statusCode",HttpStatus.UNAUTHORIZED.value());


                response.getWriter().write(new ObjectMapper().writeValueAsString(respuesta));
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(TokenJwtConfig.CONTENT_TYPE);
            }

        } catch (JwtException e) {
            log.error("Error al procesar el token JWT: {}", e.getMessage());

            respuesta.put("error", e.getMessage());
            respuesta.put("mensaje", "El token JWT es incorrecto");
            respuesta.put("statusCode",HttpStatus.UNAUTHORIZED.value());



            response.getWriter().write(new ObjectMapper().writeValueAsString(respuesta));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(TokenJwtConfig.CONTENT_TYPE);
        } catch (Exception e) {
            log.error("Error inesperado al validar el token: {}", e.getMessage());

            respuesta.put("error", "Error al realizar la autenticación: " + e.getMessage());
            respuesta.put("mensaje", "Error al validar el token");
            respuesta.put("statusCode",HttpStatus.INTERNAL_SERVER_ERROR.value());



            response.getWriter().write(new ObjectMapper().writeValueAsString(respuesta));
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType(TokenJwtConfig.CONTENT_TYPE);
        }
    }

    private boolean isTokenExpired(Date exp) {
        Date currentDate = new Date();
        return exp.before(currentDate);
    }


}
