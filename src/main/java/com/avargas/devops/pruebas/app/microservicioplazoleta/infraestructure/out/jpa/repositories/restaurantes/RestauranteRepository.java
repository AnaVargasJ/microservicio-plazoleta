package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.restaurantes;

import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
}