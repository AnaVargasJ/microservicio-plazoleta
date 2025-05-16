package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.platos;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.platos.IPlatoServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.platos.PlatoDataException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.exception.platos.PlatoInvalidoException;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PlatoModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.platos.PlatoPersistencePort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class PlatoUseCase implements IPlatoServicePort {

    private final PlatoPersistencePort persistencePort;
    @Override
    public void  crearPlato(PlatoModel plato) {
        validar(plato);
        persistencePort.guardar(plato);
    }
    @Override
    public PlatoModel getPlatoModelById(Long id) {
        PlatoModel platoModel = persistencePort.getPlatoModelById(id);
        if (platoModel == null) {
            throw new PlatoDataException("No existe  plato con el " + id);
        }
        return platoModel;
    }

    @Override
    public void modificarPlato(Long id, PlatoModel platoActualizado) {
        PlatoModel platoExistente = getPlatoModelById(id);
        platoExistente.setDescripcion(platoActualizado.getDescripcion() != null
                        ? platoActualizado.getDescripcion()
                        : platoExistente.getDescripcion()
        );

        platoExistente.setPrecio(platoActualizado.getPrecio() != null
                        ? platoActualizado.getPrecio()
                        : platoExistente.getPrecio()
        );
        persistencePort.updatePlatoDescripcionPrecio(id,platoExistente.getDescripcion(), platoActualizado.getPrecio());
    }

    @Override
    public void activarDesactivarPlato(Long id, Boolean activo, Long idPropietario) {
      getPlatoModelById(id);
        if (!persistencePort.validarPropietarioDelPlato(id, idPropietario))
            throw new PlatoInvalidoException("Solo el propietario puede habilitar/deshabilitar platos.");
        persistencePort.activarDesactivarPlato(id, activo);
    }

    private void validar(PlatoModel plato) {
        if (plato == null) {
            throw new PlatoInvalidoException("El plato no puede ser nulo.");
        }
        if (plato.getNombre() == null || plato.getNombre().isBlank()) {
            throw new PlatoInvalidoException("El nombre del plato es obligatorio.");
        }
        if (plato.getDescripcion() == null || plato.getDescripcion().isBlank()) {
            throw new PlatoInvalidoException("La descripción del plato es obligatoria.");
        }
        if (plato.getPrecio() == null || plato.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PlatoInvalidoException("El precio debe ser mayor que cero.");
        }
    }
}
