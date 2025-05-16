package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.platos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PlatoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

public interface PlatoRepository extends JpaRepository<PlatoEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE PlatoEntity p SET p.descripcion = :descripcion, p.precio = :precio WHERE p.id = :id")
    int actualizarDescripcionYPrecio(Long id, String descripcion, BigDecimal precio);


    @Modifying
    @Transactional
    @Query("UPDATE PlatoEntity p SET p.activo = :activo WHERE p.id = :id")
    int activarDesactivarPlato(Long id, Boolean activo);

    @Query("SELECT p FROM PlatoEntity p " +
            "JOIN FETCH p.categoriaEntity " +
            "JOIN FETCH p.restauranteEntity " +
            "WHERE p.id = :id")
    Optional<PlatoEntity> findByIdWithRelations(@Param("id") Long id);

    @Query("""
    SELECT CASE 
             WHEN COUNT(p) > 0 THEN true 
             ELSE false 
           END 
    FROM PlatoEntity p 
    WHERE p.id = :idPlato 
      AND p.restauranteEntity.idPropietario = :idUsuario
""")
    Boolean existsPlatoOwnedByUsuario(@Param("idPlato") Long idPlato, @Param("idUsuario") Long idUsuario);


    Page<PlatoEntity> findByRestauranteEntityIdAndCategoriaEntityIdAndActivoTrue(Long idRestaurante, Long idCategoria, Pageable pageable);


}