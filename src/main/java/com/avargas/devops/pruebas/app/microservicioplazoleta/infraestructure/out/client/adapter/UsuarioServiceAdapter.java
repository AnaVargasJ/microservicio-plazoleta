package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.client.adapter;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.restaurante.UsuarioServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.restaurante.ValidacionNegocioException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.client.IGenericHttpClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UsuarioServiceAdapter implements UsuarioServicePort {

    private final IGenericHttpClient genericHttpClient;

    @Value("${microserviciopropietarios}")
    private String urlPropietarios;

    @Override
    public Long usuarioEsPropietario(String correo, HttpServletRequest request) {
        String token = "Bearer " + request.getHeader("Authorization");
        String url = this.urlPropietarios + "/buscarPorCorreo/{correo}";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        String finalUrl = builder.buildAndExpand(correo).toUriString();
        Map<String, String> headers = Map.of(HttpHeaders.AUTHORIZATION, token);
        Map<String, Object> response = genericHttpClient.sendRequest(finalUrl, HttpMethod.GET, null, headers);

        if (response == null || !response.containsKey("respuesta")) {
            throw new ValidacionNegocioException("No se pudo obtener la información del usuario propietario.");
        }

        Object respuestaObj = response.get("respuesta");
        if (!(respuestaObj instanceof Map)) {
            throw new ValidacionNegocioException("La respuesta del servicio de usuarios es inválida.");
        }

        Map<?, ?> respuesta = (Map<?, ?>) respuestaObj;
        Object rolObj = respuesta.get("rol");

        if (!(rolObj instanceof Map)) {
            throw new ValidacionNegocioException("El usuario no tiene rol de propietario.");
        }


        Map<?, ?> rol = (Map<?, ?>) rolObj;
        Object nombreRol = rol.get("nombre");

        if (nombreRol == null) {
            throw new ValidacionNegocioException("El rol del usuario no está disponible.");
        }

        String rolStr = nombreRol.toString().toUpperCase();
        if (rolStr.equals("PROP")) {
            Object idUsuario = respuesta.get("idUsuario");
            if (!(idUsuario instanceof Number)) {
                throw new ValidacionNegocioException("No se pudo obtener el ID del propietario.");
            }
            return ((Number) idUsuario).longValue();
        }
        throw new ValidacionNegocioException("El rol '" + rolStr + "' no tiene permitido crear solicitudes.");
    }
}
