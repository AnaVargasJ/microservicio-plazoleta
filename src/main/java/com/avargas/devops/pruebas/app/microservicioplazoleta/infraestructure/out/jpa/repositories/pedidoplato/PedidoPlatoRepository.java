package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.pedidoplato;

import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoPlatoEntity;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoPlatoIdEmbeddable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PedidoPlatoRepository extends JpaRepository<PedidoPlatoEntity, PedidoPlatoIdEmbeddable> {



}