package com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.platos.impl;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.request.PlatoDTOUpdate;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.services.platos.IPlatoService;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.converter.GenericConverter;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.Categoria;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.Plato;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.entities.Restaurante;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.categorias.CategoriaRepository;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.platos.PlatoRepository;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.restaurantes.RestauranteRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlatoService implements IPlatoService {

    private  final PlatoRepository platoRepository;

    private final GenericConverter genericConverter;

    private final RestauranteRepository restauranteRepository;
    private  final CategoriaRepository categoriaRepository;


    @Override
    @Transactional
    public ResponseEntity<?> crearPlato(HttpServletRequest request, PlatoDTO platoDTO) {
        Plato plato = new Plato();
        try{
        Restaurante restaurante = restauranteRepository.findById(platoDTO.getIdRestaurante()).get();

        Categoria categoria = categoriaRepository.findById(platoDTO.getIdCategoria()).get();

        plato = genericConverter.mapDtoToEntity(platoDTO, Plato.class);
        plato.setRestaurante(restaurante);
        plato.setCategoria(categoria);
        plato.setActivo(Boolean.TRUE);
        platoRepository.save(plato);

        return new ResponseEntity<>("Plato creado correctamente", HttpStatus.CREATED);

        }catch (Exception e) {
            log.error("Error al crear el plato: {}", e.getMessage());
            return new ResponseEntity<>("Error al crear el plato", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> modificarPlato(HttpServletRequest request, Long id, PlatoDTOUpdate platoDTO) {
        try {
            Map<String, Object> respuesta = new HashMap<>();

            ResponseEntity<Map<String, Object>> response = platoRepository.findById(id)
                    .map(plato -> {
                        plato.setDescripcion(platoDTO.getDescripcion());
                        plato.setPrecio(platoDTO.getPrecio());
                        platoRepository.save(plato);

                        respuesta.put("mensaje", "Plato modificado correctamente");
                        respuesta.put("codigo", HttpStatus.OK.value());
                        return new ResponseEntity<>(respuesta, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        respuesta.put("mensaje", "No se encontró el plato con ID " + id);
                        respuesta.put("codigo", HttpStatus.NOT_FOUND.value());
                        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
                    });

            return response;
        } catch (Exception e) {
            log.error("Error al modificar el plato: {}", e.getMessage());
            return new ResponseEntity<>("Error al modificar el plato", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
