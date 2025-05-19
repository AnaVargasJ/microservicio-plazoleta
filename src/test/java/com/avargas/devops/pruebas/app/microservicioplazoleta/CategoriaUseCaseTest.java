package com.avargas.devops.pruebas.app.microservicioplazoleta;


import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.categorias.CategoriaDataException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.CategoriaModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.categorias.CategoriaPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.categorias.CategoriaUseCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoriaUseCaseTest {

    @Mock
    private CategoriaPersistencePort categoriaPersistencePort;

    @InjectMocks
    private CategoriaUseCase categoriaUseCase;

    @Test
    @Order(1)
    void getCategoriaModelById_existente_retornaCategoria() {
        Long id = 1L;
        CategoriaModel esperada = CategoriaModel.builder()
                .id(id)
                .nombre("Bebidas")
                .build();

        when(categoriaPersistencePort.getCategoriaModelById(id)).thenReturn(esperada);

        CategoriaModel resultado = categoriaUseCase.getCategoriaModelById(id);

        assertEquals(esperada, resultado);
        verify(categoriaPersistencePort).getCategoriaModelById(id);
    }

    @Test
    @Order(2)
    void getCategoriaModelById_noExistente_lanzaExcepcion() {
        Long id = 99L;

        when(categoriaPersistencePort.getCategoriaModelById(id)).thenReturn(null);

        CategoriaDataException ex = assertThrows(
                CategoriaDataException.class,
                () -> categoriaUseCase.getCategoriaModelById(id)
        );

        assertEquals("No existe la categoria con el" + id, ex.getMessage());
        verify(categoriaPersistencePort).getCategoriaModelById(id);
    }


}
