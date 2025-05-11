package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class GenericHttpClient {

    private final WebClient.Builder webClientBuilder;

    public Map<String, Object> sendRequest(String url, HttpMethod method, Map<String, Object> body,
                                           Map<String, String> headers) {

        WebClient.RequestBodySpec requestSpec = webClientBuilder
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build()
                .method(method)
                .uri(url);

        WebClient.RequestHeadersSpec<?> headersSpec = body != null
                ? requestSpec.bodyValue(body)
                : requestSpec;

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                headersSpec = headersSpec.header(entry.getKey(), entry.getValue());
            }
        }
        try {
            return headersSpec
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response -> {
                        return Mono.error(new RuntimeException("Error HTTP: " + response.statusCode()));
                    })
                    .bodyToMono(Map.class)
                    .block();
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error en la solicitud HTTP: " + e.getMessage());
        }


    }
}
