package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.client.adapter;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.pedido.INotificacionServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.restaurante.ValidacionNegocioException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.client.IGenericHttpClient;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.EndpointApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;


@RequiredArgsConstructor
public class NotificacionServiceAdapter implements INotificacionServicePort {

    private final IGenericHttpClient genericHttpClient;

    @Value("${microservicioTrazabilidad}")
    private String urlPropietarios;


    private static final String ERROR_SERVICIO_MENSAJERIA = "No se obtuvo una respuesta válida del servicio de mensajería.";

    private static final String ERROR_RESPUESTA_MENSAJERIA = "El servicio de mensajería respondió con error.";

    private static final String CODIGO = "codigo";
    private static final int CODIGO_EXITO = 200;

    @Override
    public Boolean notificarUsuario(String token, Long idUsuario, String mensaje) {

        String url = this.urlPropietarios + EndpointApi.ENVIAR_NOTIFICACION_ID_USUARIO;

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        String finalUrl = builder.buildAndExpand(idUsuario, mensaje).toUriString();

        Map<String, String> headers = Map.of(HttpHeaders.AUTHORIZATION, token);
        Map<String, Object> response = genericHttpClient.sendRequest(finalUrl, HttpMethod.POST, null, headers);

        if (response == null || !response.containsKey(CODIGO)) {
            throw new ValidacionNegocioException(ERROR_SERVICIO_MENSAJERIA);
        }

        Object codigoObj = response.get(CODIGO);
        if (!(codigoObj instanceof Number) || ((Number) codigoObj).intValue() != CODIGO_EXITO) {
            throw new ValidacionNegocioException(ERROR_RESPUESTA_MENSAJERIA);
        }

        return Boolean.TRUE;
    }


}
