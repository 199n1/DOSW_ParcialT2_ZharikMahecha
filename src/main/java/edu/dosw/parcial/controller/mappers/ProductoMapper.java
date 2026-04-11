package edu.dosw.parcial.controller.mappers;

import edu.dosw.parcial.controller.dtos.response.ProductoResponse;
import edu.dosw.parcial.core.models.Producto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    ProductoResponse toResponse(Producto producto);
}