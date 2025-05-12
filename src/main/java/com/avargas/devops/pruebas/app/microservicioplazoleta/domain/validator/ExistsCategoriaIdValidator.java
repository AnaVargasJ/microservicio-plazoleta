package com.avargas.devops.pruebas.app.microservicioplazoleta.domain.validator;

import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.categorias.CategoriaRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ExistsCategoriaIdValidator implements ConstraintValidator<ExistsCategoriaId, Long> {


    private final  CategoriaRepository categoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return categoriaRepository.findById(value).isPresent();
    }
}


