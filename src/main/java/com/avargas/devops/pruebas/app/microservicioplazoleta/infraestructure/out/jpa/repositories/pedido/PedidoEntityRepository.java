package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoEntityRepository extends JpaRepository<PedidoEntity, Long> {

    Boolean existsByIdClienteAndEstadoIn(Long idCliente, List<String> estados);

    @Query("SELECT p FROM PedidoEntity p WHERE p.estado = :estado AND p.restauranteEntity.id = :idRestaurante")
    Page<PedidoEntity> findByEstadoAndRestauranteId(
            @Param("estado") String estado,
            @Param("idRestaurante") Long idRestaurante,
            Pageable pageable);

}