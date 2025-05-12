package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.categorias;

import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {


}