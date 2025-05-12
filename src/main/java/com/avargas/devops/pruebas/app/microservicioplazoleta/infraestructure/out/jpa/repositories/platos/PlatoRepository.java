package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.platos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.Plato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatoRepository extends JpaRepository<Plato, Long> {

}