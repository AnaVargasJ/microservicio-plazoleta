package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.pedidoplato;

import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoPlatoEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoPlatoIdEmbeddable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoPlatoRepository extends JpaRepository<PedidoPlatoEntity, PedidoPlatoIdEmbeddable> {
}