package com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.restaurante;

import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.RestauranteResumenDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestauranteResponseMapper {

    List<RestauranteResumenDTO> toResponseRestaurantesList(List<RestauranteModel> restauranteModelList);
}
