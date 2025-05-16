package com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.plato;



import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.PlatoResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PlatoModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPlatoResponseMapper {

    List<PlatoResponseDTO> toResponsePlatosModelList(List<PlatoModel> platoModelList);
}
