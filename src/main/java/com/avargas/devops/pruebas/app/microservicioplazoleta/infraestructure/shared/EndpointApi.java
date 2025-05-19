package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared;

public interface EndpointApi {

    String BASE_PATH_PLATOS = "/api/v1/plato";
    String BASE_PATH_RESTAURANTE= "/api/v1/plazoleta";
    String BASE_PATH_PEDIDOS= "/api/v1/pedidos";

    String LIST_RESTAURANTES = "/restaurantes";
    String CREATE_RESTAURANTE = "/crearRestaurante";

    String CREATE_PLATOS = "/crearPlato";
    String CREATE_PEDIDOS = "/crearPedido";

    String ASIGNAR_PEDIDOS = "/pedidos/{id}/asignar/{estado}";

    String UPDATE_PLATO = "/modificarPlato/{idPlato}";

    String DISABLE_PLATO = "/plato/{id}/estado";

    String LIST_PLATOS = "/{idRestaurante}/platos";

    String LIST_PEDIDOS_BY_ESTADO = "/obtenerPedido/{estado}/restaurante/{idRestaurante}";


}
