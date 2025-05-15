package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.platos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PlatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface PlatoRepository extends JpaRepository<PlatoEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE PlatoEntity p SET p.descripcion = :descripcion, p.precio = :precio WHERE p.id = :id")
    int actualizarDescripcionYPrecio(Long id, String descripcion, BigDecimal precio);

}