package com.avargas.devops.pruebas.app.microservicioplazoleta.application.repositories.plazoleta;

import com.avargas.devops.pruebas.app.microservicioplazoleta.model.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
}