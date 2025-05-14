package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.client.adapter;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.UsuarioServicePort;
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
    public Boolean usuarioEsPropietario(String correo, HttpServletRequest request) {
        String  token = "Bearer " + request.getHeader("Authorization");
        String url = this.urlPropietarios + "/buscarPorCorreo/{correo}";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        String finalUrl = builder.buildAndExpand(correo).toUriString();
        Map<String, String> headers = Map.of(HttpHeaders.AUTHORIZATION, token);
        Map<String, Object> response = genericHttpClient.sendRequest(finalUrl, HttpMethod.GET, null, headers);

        if (response == null || !response.containsKey("respuesta")) {
            return Boolean.FALSE;
        }

        Object respuestaObj = response.get("respuesta");
        if (!(respuestaObj instanceof Map)) {
            return Boolean.FALSE;
        }

        Map<?, ?> respuesta = (Map<?, ?>) respuestaObj;
        Object rolObj = respuesta.get("rol");

        if (!(rolObj instanceof Map)) {
            return Boolean.FALSE;
        }

        Map<?, ?> rol = (Map<?, ?>) rolObj;
        Object nombreRol = rol.get("nombre");

        return nombreRol != null && "PROP".equalsIgnoreCase(nombreRol.toString());
    }
}
