package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.pedido;

import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoEntityRepository extends JpaRepository<PedidoEntity, Long> {

    Boolean existsByIdClienteAndEstadoIn(Long idCliente, List<String> estados);
}