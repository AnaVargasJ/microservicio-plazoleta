package com.avargas.devops.pruebas.app.microservicioplazoleta.application.mapper.restaurante;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.PageModel;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.application.dto.response.RestauranteResumenDTO;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.model.RestauranteModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IPageRestauranteResponseMapper {
    @Mapping(source = "content", target = "content")
    @Mapping(source = "currentPage", target = "currentPage")
    @Mapping(source = "pageSize", target = "pageSize")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "totalPages", target = "totalPages")
    @Mapping(source = "hasNext", target = "hasNext")
    @Mapping(source = "hasPrevious", target = "hasPrevious")
    PageResponseDTO<RestauranteResumenDTO> toResponse(PageModel<RestauranteModel> pageModel);
}