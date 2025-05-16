package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.platos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PlatoModel;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;


public interface PlatoPersistencePort {
    PlatoModel guardar(PlatoModel platoModel);
    PlatoModel getPlatoModelById(Long id);

    PlatoModel updatePlatoDescripcionPrecio(Long id, String descripcion, BigDecimal Precio);

    PlatoModel activarDesactivarPlato(Long id, Boolean activo);

    Boolean validarPropietarioDelPlato(Long idPlato, Long idUsuario);

    Page<PlatoModel> listarPlatosRestaurante(Long idRestaurante, Long idCategoria, int page, int size) ;

}
