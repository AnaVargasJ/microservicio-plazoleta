package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.restaurante.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.converter.GenericConverter;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.restaurantes.RestauranteRepository;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.restaurante.IRestauranteService;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.RestauranteDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.client.GenericHttpClient;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.Restaurante;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestauranteService implements IRestauranteService {

    private final GenericConverter genericConverter;

    private final GenericHttpClient genericHttpClient;


    private final RestauranteRepository restauranteRepository;

    @Value("${microserviciopropietarios}")
    private String urlPropietarios;

    @Override
    @Transactional
    public ResponseEntity<?> crearRestaurante(HttpServletRequest request, RestauranteDTO restauranteDTO) {

        Restaurante restaurante = new Restaurante();


        try {

            String token = "Bearer " + request.getHeader("Authorization");
            String url = this.urlPropietarios + "/buscarPorCorreo/{correo}";
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            String finalUrl = builder.buildAndExpand(restauranteDTO.getCorreo()).toUriString();
            Map<String, String> headers = Map.of(HttpHeaders.AUTHORIZATION, token);
            Map<String, Object> response = genericHttpClient.sendRequest(finalUrl, HttpMethod.GET, null, headers);

            if (response.containsKey("codigo")) {

                Map<String, Object> respuesta = new HashMap<>();

                String mensaje = (String) response.getOrDefault("mensaje", "Error desconocido");
                log.error("Error al consultar el usuario por correo: {}", mensaje);
                respuesta.put("error", "Error al consultar el rol del usuario: ");
                respuesta.put("mensaje", mensaje);
                respuesta.put("codigo", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);

            }
            Map<String, Object> rol = (Map<String, Object>) response.get("rol");
            String codigoRol = (String) rol.get("nombre");
            if (codigoRol.equals("PROP")) {

                Long idRol = rol.get("idRol") != null ? Long.parseLong(rol.get("idRol").toString()) : null;
                log.info("ID del Rol: {}", idRol);
                log.info("Código del Rol: {}", codigoRol);
                restaurante = genericConverter.mapDtoToEntity(restauranteDTO, Restaurante.class);
                restaurante.setIdPropietario(idRol);

                restauranteRepository.save(restaurante);

                return new ResponseEntity<>("Restaurante creado correctamente", HttpStatus.CREATED);
            }
            Map<String, Object> respuesta = new HashMap<>();
            log.error("El usuario con correo {} no tiene el rol propietario (rol actual: {})", restauranteDTO.getCorreo(), codigoRol);
            respuesta.put("error", "Rol no autorizado");
            respuesta.put("mensaje", "El correo no pertenece a un usuario con rol propietario.");
            respuesta.put("codigo", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(respuesta, HttpStatus.FORBIDDEN);




        } catch (Exception e) {
            log.error("Error al crear el Restaurante: {}", e.getMessage());
            return new ResponseEntity<>("Error al crear el Restaurante", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
