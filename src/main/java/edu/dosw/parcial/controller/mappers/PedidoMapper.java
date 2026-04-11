package edu.dosw.parcial.controller.mappers;

import edu.dosw.parcial.controller.dtos.response.ItemPedidoResponse;
import edu.dosw.parcial.controller.dtos.response.PedidoResponse;
import edu.dosw.parcial.core.models.ItemPedido;
import edu.dosw.parcial.core.models.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    PedidoResponse toResponse(Pedido pedido);

    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.nombre", target = "nombreProducto")
    @Mapping(expression = "java(item.getProducto().getPrecio() * item.getCantidad())",
            target = "subtotal")
    ItemPedidoResponse toItemResponse(ItemPedido item);
}