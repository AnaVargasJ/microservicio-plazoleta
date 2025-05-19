package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.shared;



public final class SwaggerConstants {

    private SwaggerConstants() {}

    // === Tags ===
    public static final String TAG_PLATO = "Plato";
    public static final String TAG_PLATO_DESC = "Aplicación que crea platos para asociarlos a un restaurante.";

    public static final String TAG_RESTAURANTE = "Restaurante";
    public static final String TAG_RESTAURANTE_DESC = "Aplicación que crea restaurantes por medio de un administrador.";

    public static final String TAG_PEDIDO = "Pedidos";
    public static final String TAG_PEDIDO_DESC = "Operaciones relacionadas con pedidos de clientes";


    // === Operaciones - Plato ===
    public static final String OP_CREAR_PLATO_SUMMARY = "Crear Plato";
    public static final String OP_CREAR_PLATO_DESC = "Permite al propietario de un restaurante crear un nuevo plato y asociarlo a una categoría y restaurante.";

    public static final String OP_MODIFICAR_PLATO_SUMMARY = "Modificar Plato";
    public static final String OP_MODIFICAR_PLATO_DESC = "Permite al propietario modificar la descripción o precio de un plato existente.";

    public static final String OP_CAMBIAR_ESTADO_PLATO_SUMMARY = "Habilitar o deshabilitar un plato del menú";
    public static final String OP_LISTAR_PLATOS_SUMMARY = "Listar platos por categoría";
    public static final String OP_LISTAR_PLATOS_DESC = "Retorna un listado de platos por menú de restaurante, con paginación y filtro opcional por categoría.";

    // === Operaciones - Restaurante ===
    public static final String OP_CREAR_RESTAURANTE_SUMMARY = "Crear Restaurante";
    public static final String OP_CREAR_RESTAURANTE_DESC = "Crear un nuevo restaurante en la base de datos con rol usuario administrador.";

    public static final String OP_LISTAR_RESTAURANTES_SUMMARY = "Listar restaurantes disponibles";
    public static final String OP_LISTAR_RESTAURANTES_DESC = "Retorna un listado paginado y ordenado alfabéticamente de restaurantes.";

    // === Operaciones Pedidos ===

    public static final String OP_CREAR_PEDIDO_SUMMARY = "Crear un nuevo pedido";
    public static final String OP_ASIGNAR_PEDIDO_SUMMARY = "Asignar pedido a empleado";
    public static final String OP_LISTA_PEDIDO_SUMMARY = "Lista de pedidos por estado";
    public static final String OP_CREAR_PEDIDO_DESC = """
        El cliente realiza un pedido de platos a un restaurante. 
        El pedido debe cumplir con las reglas del negocio:
        - Todos los platos deben pertenecer al mismo restaurante.
        - El cliente no debe tener otro pedido en proceso.
        - El estado inicial del pedido será 'PENDIENTE'.
        """;

    public static final String OP_ASIGNAR_PEDIDO_DESC = "Permite a un empleado asignarse un pedido pendiente y cambiar su estado a 'EN_PREPARACION'. " +
            "El pedido debe pertenecer al restaurante del empleado y estar en estado 'PENDIENTE'";

    public static final String OP_LISTA_PEDIDO_DESC = "Obtener lista de pedidos filtrando por estado";

    public static final String DESC_ID_PEDIDO = "ID del pedido a asignar";
    public static final String DESC_ESTADO_PEDIDO = "Estado del pedido a asignar";

    //===Parameters descripciones pedidos

    public static final String DESC_PEDIDO_REGISTRAR = "Datos del pedido a registrar";

    // === Respuestas comunes ===
    public static final String RESPONSE_200_DESC = "Operación realizada correctamente.";
    public static final String RESPONSE_201_DESC = "Recurso creado correctamente.";
    public static final String RESPONSE_400_DESC = "Solicitud inválida o datos incorrectos.";
    public static final String RESPONSE_401_DESC = "Token inválido o no enviado.";
    public static final String RESPONSE_403_DESC = "Acceso denegado: no tiene permisos suficientes.";
    public static final String RESPONSE_404_DESC = "Recurso no encontrado.";
    public static final String RESPONSE_500_DESC = "Error interno del servidor.";
}