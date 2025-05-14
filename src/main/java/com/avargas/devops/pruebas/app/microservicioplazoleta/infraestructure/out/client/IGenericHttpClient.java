package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.client;

import org.springframework.http.HttpMethod;

import java.util.Map;

public interface IGenericHttpClient {

    Map<String, Object> sendRequest(String url, HttpMethod method, Map<String, Object> body,
                                    Map<String, String> headers);
}
