package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.client.adapter;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.pedido.ITrazabilidadServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.restaurante.ValidacionNegocioException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PedidoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.client.IGenericHttpClient;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared.EndpointApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
public class TrazabilidadServiceAdapter implements ITrazabilidadServicePort {

    private final IGenericHttpClient genericHttpClient;

    @Value("${microservicioTrazabilidad}")
    private String urlTrazabilidad;

    private static final String CODIGO = "codigo";
    private static final int CODIGO_EXITO = 200;

    private static final String ERROR_SERVICIO_TRAZABILIDAD = "No se obtuvo una respuesta válida en el servicio de traza";
    @Override
    public void crearTraza(String token, PedidoModel pedidoModel, String estadoNuevo, Long idEmpleado,String correoEmpleado, String correoCliente) {
        String url = this.urlTrazabilidad + EndpointApi.CREATE_TRAZABILIDAD ;

        Map<String, Object> body = mapearPedidoATrazabilidad(pedidoModel, estadoNuevo, idEmpleado, correoEmpleado, correoCliente);

        Map<String, String> headers = Map.of(HttpHeaders.AUTHORIZATION, token);


        Map<String, Object> response = genericHttpClient.sendRequest(url, HttpMethod.POST, body, headers);

        if (response == null || !response.containsKey(CODIGO)) {
            throw new ValidacionNegocioException(ERROR_SERVICIO_TRAZABILIDAD);
        }
    }

    private Map<String, Object> mapearPedidoATrazabilidad(PedidoModel pedido, String estadoNuevo, Long idEmpleado, String correoEmpleado, String correoCliente) {
        Map<String, Object> body = new HashMap<>();
        body.put("idPedido", pedido.getId());
        body.put("idCliente", String.valueOf(pedido.getIdCliente()));
        body.put("correoCliente", correoCliente);
        body.put("fecha", LocalDateTime.now().toString());
        body.put("estadoAnterior", pedido.getEstado());
        body.put("estadoNuevo", estadoNuevo);
        body.put("idEmpleado", idEmpleado);
        body.put("correoEmpleado", correoEmpleado);

        return body;
    }

}
