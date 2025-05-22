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
    String CANCELAR_PEDIDOS = "/pedidos/{id}/asignar";
    String FILTRAR_PEDIDOS_ID_RESTAURANTE = "/pedidos/{idRestaurante}";
    String ENTREGAR_PEDIDO = "/pedidos/{id}/entregar/{estado}/cliente/{pin}";

    String UPDATE_PLATO = "/modificarPlato/{idPlato}";



    String DISABLE_PLATO = "/plato/{id}/estado";

    String LIST_PLATOS = "/{idRestaurante}/platos";

    String LIST_PEDIDOS_BY_ESTADO = "/obtenerPedido/{estado}/restaurante/{idRestaurante}";

    String ENVIAR_NOTIFICACION_ID_USUARIO =  "/enviar-notificacion/{idUsuario}/pedido/{mensaje}";
    String CREATE_TRAZABILIDAD = "/crearTrazabilidad";

    String BUSCAR_USUARIO_POR_ID =  "/buscarPorIdUsuario/{idUsuario}";


}
